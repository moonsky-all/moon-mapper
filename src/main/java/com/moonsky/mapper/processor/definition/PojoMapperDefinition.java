package com.moonsky.mapper.processor.definition;

import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.mapper.processor.holder.MapperHolders;
import com.moonsky.mapper.util.NamingStrategy;
import com.moonsky.processor.processing.filer.JavaFileDetails;
import com.moonsky.processor.processing.filer.JavaSupplier;
import com.moonsky.processor.processing.generate.JavaFileClassDetails;
import com.moonsky.processor.processing.generate.ScopedFields;
import com.moonsky.processor.processing.util.Const2;

import javax.lang.model.element.Modifier;
import java.util.Map;

/**
 * @author benshaoye
 */
public class PojoMapperDefinition extends PojoBaseDefinition implements JavaSupplier {

    private final MapperNaming namingAttributes;
    private final PojoCopierDefinition forward;
    private final PojoCopierDefinition backward;

    public PojoMapperDefinition(
        MapperHolders holders,
        MapperNaming namingAttributes,
        PojoCopierDefinition forwardCopierRecord,
        PojoCopierDefinition backwardCopierRecord
    ) {
        super(holders, forwardCopierRecord.getThisPojoDeclared(), forwardCopierRecord.getThatPojoDeclared());
        this.namingAttributes = namingAttributes;
        this.forward = forwardCopierRecord;
        this.backward = backwardCopierRecord;
    }

    public String getSimpleName() {
        String thisClass = getThisDeclared().getThisSimpleName();
        String thatClass = getThatDeclared().getThisSimpleName();
        return NamingStrategy.mapperOf(namingAttributes, thisClass, thatClass);
    }

    @Override
    public JavaFileDetails get() {
        String packageName = getPackageName();
        String classname = getSimpleName();
        JavaFileClassDetails definition = new JavaFileClassDetails(packageName, classname);
        definition.impls().implement("{}<{}, {}>", Const2.BeanMapper_ClassName, getThisClassname(), getThatClassname());
        definition.annotateComponent().annotateMapperImplGenerated();

        ScopedFields fields = definition.fields();
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
