package com.moonsky.processing.definition;

import com.moonsky.mapper.BeanMapper;
import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.mapper.util.Keyword;
import com.moonsky.processing.generate.JavaFileClassDefinition;
import com.moonsky.processing.generate.JavaScopedFields;
import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.processor.JavaDefinition;
import com.moonsky.processing.processor.JavaSupplier;
import com.moonsky.processing.util.Const2;

import javax.lang.model.element.Modifier;

/**
 * @author benshaoye
 */
public class PojoMapperDefinition extends PojoBaseDefinition implements JavaSupplier {

    private final MapperFor mapperFor;
    private final PojoCopierDefinition forward;
    private final PojoCopierDefinition backward;

    public PojoMapperDefinition(
        Holders holders,
        MapperFor mapperFor,
        PojoCopierDefinition forwardCopierRecord,
        PojoCopierDefinition backwardCopierRecord
    ) {
        super(holders, forwardCopierRecord.getThisPojoDeclared(), forwardCopierRecord.getThatPojoDeclared());
        this.mapperFor = mapperFor;
        this.forward = forwardCopierRecord;
        this.backward = backwardCopierRecord;
    }

    public String getSimpleName() {
        String thisClass = getThisDeclared().getThisSimpleName();
        String thatClass = getThatDeclared().getThisSimpleName();
        return Keyword.mapperOf(mapperFor, thisClass, thatClass);
    }

    @Override
    public JavaDefinition get() {
        String packageName = getPackageName();
        String classname = getSimpleName();
        JavaFileClassDefinition definition = new JavaFileClassDefinition(packageName, classname);
        definition.impls().implement("{}<{}, {}>", BeanMapper.class, getThisClassname(), getThatClassname());
        definition.annotateComponent().annotateMapperImplGenerated();

        JavaScopedFields fields = definition.fields();
        fields.declareField(Const2.FORWARD, forward.getClassname())
            .assign()
            .valueOfStaticRef(forward.getClassname(), Const2.CONST)
            .end()
            .modifierWithAll(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        fields.declareField(Const2.BACKWARD, backward.getClassname())
            .assign()
            .valueOfStaticRef(backward.getClassname(), Const2.CONST)
            .end()
            .modifierWithAll(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);

        definition.methods()
            .declareMethod("unsafeForward", param -> {
                param.add(THIS, getThisClassname());
                param.add(THAT, getThatClassname());
            })
            .typeOf(getThatClassname())
            .scripts()
            .returning("{}.unsafeCopy({}, {})", Const2.FORWARD, THIS, THAT)
            .end()
            .annotateOverride();
        definition.methods()
            .declareMethod("unsafeBackward", param -> {
                param.add(THIS, getThisClassname());
                param.add(THAT, getThatClassname());
            })
            .typeOf(getThisClassname())
            .scripts()
            .returning("{}.unsafeCopy({}, {})", Const2.BACKWARD, THAT, THIS)
            .end()
            .annotateOverride();
        definition.methods()
            .declareMethod("doForward", param -> {
                param.add(THIS, getThisClassname());
            })
            .typeOf(getThatClassname())
            .scripts()
            .returning("{}.convert({})", Const2.FORWARD, THIS)
            .end()
            .annotateOverride();
        definition.methods()
            .declareMethod("doBackward", param -> {
                param.add(THAT, getThatClassname());
            })
            .typeOf(getThisClassname())
            .scripts()
            .returning("{}.convert({})", Const2.BACKWARD, THAT)
            .end()
            .annotateOverride();

        return definition;
    }
}
