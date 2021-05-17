package com.moonsky.processing.definition;

import com.moonsky.mapper.BeanCopier;
import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.mapper.util.Keyword;
import com.moonsky.processing.declared.PojoDeclared;
import com.moonsky.processing.declared.PropertyDeclared;
import com.moonsky.processing.declared.PropertyMethodDeclared;
import com.moonsky.processing.generate.JavaCodeBlockAddr;
import com.moonsky.processing.generate.JavaElemMethod;
import com.moonsky.processing.generate.JavaFileClassDefinition;
import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.processor.JavaDefinition;
import com.moonsky.processing.processor.JavaSupplier;
import com.moonsky.processing.util.Const2;
import com.moonsky.processing.util.String2;
import com.moonsky.processing.wrapper.Import;

import javax.lang.model.element.Modifier;
import java.util.Map;

/**
 * @author benshaoye
 */
public class PojoCopierDefinition extends PojoBaseDefinition implements JavaSupplier {

    private final String simpleName;
    private final String classname;

    public PojoCopierDefinition(
        Holders holders, MapperFor mapperFor, PojoDeclared thisDeclared, PojoDeclared thatDeclared
    ) {
        super(holders, thisDeclared, thatDeclared);
        String thisClass = getThisDeclared().getThisSimpleName();
        String thatClass = getThatDeclared().getThisSimpleName();
        this.simpleName = Keyword.copierOf(mapperFor, thisClass, thatClass);
        this.classname = getPackageName() + '.' + simpleName;
    }

    public String getClassname() { return classname; }

    public String getSimpleName() { return simpleName; }

    @Override
    public JavaDefinition get() {
        String packageName = getPackageName();
        String classname = getSimpleName();
        JavaFileClassDefinition definition = new JavaFileClassDefinition(packageName, classname);
        definition.impls().implement("{}<{}, {}>", BeanCopier.class, getThisClassname(), getThatClassname());
        definition.annotateComponent().annotateCopierImplGenerated();

        definition.fields()
            .declareField(Const2.CONST, definition.getClassname())
            .assign()
            .valueOfFormatted("new {}()", Import.nameOf(definition.getClassname()))
            .end()
            .modifierWithAll(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        doConvert(definition.methods().declareMethod("convert", param -> {
            param.add(THIS, getThisClassname());
        }).typeOf(getThatClassname()).scripts().returning(null)).annotateOverride();

        doUnsafeCopy(definition.methods().declareMethod("unsafeCopy", param -> {
            param.add(THIS, getThisClassname());
            param.add(THAT, getThatClassname());
        }).typeOf(getThatClassname()).scripts().returning(THAT)).annotateOverride();

        return definition;
    }

    private JavaElemMethod doConvert(JavaCodeBlockAddr<JavaElemMethod> scripts) {
        return scripts.end();
    }

    private JavaElemMethod doUnsafeCopy(JavaCodeBlockAddr<JavaElemMethod> scripts) {
        Map<String, PropertyDeclared> thisProperties = getThisDeclared().getProperties();
        Map<String, PropertyDeclared> thatProperties = getThatDeclared().getProperties();

        for (Map.Entry<String, PropertyDeclared> propertyEntry : thisProperties.entrySet()) {
            PropertyDeclared thatProperty = thatProperties.get(propertyEntry.getKey());
            if (thatProperty == null || !thatProperty.isMaybeWritable()) {
                // 没有 setter
                continue;
            }
            PropertyDeclared thisProperty = propertyEntry.getValue();
            if (!thisProperty.isReadable()) {
                // 没有 getter
                continue;
            }

            String thisPropertyType = thisProperty.getPropertyType();
            String getterRef = thisProperty.getGetterReferenced(THIS);
            String setterScript = thatProperty.getTypeFulledSetterReferenced(THAT, getterRef, thisPropertyType);

            if (String2.isNotBlank(setterScript)) {
                scripts.scriptOf(setterScript);
            } else {
                PropertyMethodDeclared setter = thatProperty.getOnlySetterMethod();
                if (setter != null) {
                    String actualType = setter.getPropertyActualType();
                }
            }
        }
        return scripts.end();
    }
}
