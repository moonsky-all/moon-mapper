package com.moonsky.processing.definition;

import com.moonsky.mapper.BeanCopier;
import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.mapper.util.Keyword;
import com.moonsky.processing.declared.PojoDeclared;
import com.moonsky.processing.declared.PropertyDeclared;
import com.moonsky.processing.declared.PropertyMethodDeclared;
import com.moonsky.processing.generate.*;
import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.processor.JavaDefinition;
import com.moonsky.processing.processor.JavaSupplier;
import com.moonsky.processing.util.Const2;
import com.moonsky.processing.util.String2;
import com.moonsky.processing.util.Test2;
import com.moonsky.processing.wrapper.Import;

import javax.lang.model.element.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

/**
 * @author benshaoye
 */
public class PojoCopierDefinition extends PojoBaseDefinition implements JavaSupplier {

    private static final String INT_PRIMITIVE_CLASS = int.class.getCanonicalName();
    private static final String STRING_CLASS = String.class.getCanonicalName();
    private static final String INT_CAPITALIZED = String2.capitalize(int.class.getCanonicalName());
    private static final String JAVA_DOT_LANG_DOT = "java.lang.";
    private static final String BIG_DECIMAL_CLASS = BigDecimal.class.getCanonicalName();
    private static final String BIG_INTEGER_CLASS = BigInteger.class.getCanonicalName();

    private static boolean isMathNumber(String fullClassname) {
        return BIG_DECIMAL_CLASS.equals(fullClassname) || BIG_INTEGER_CLASS.equals(fullClassname);
    }

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
        }).typeOf(getThatClassname()).scripts()).annotateOverride();

        doUnsafeCopy(definition.methods().declareMethod("unsafeCopy", param -> {
            param.add(THIS, getThisClassname());
            param.add(THAT, getThatClassname());
        }).typeOf(getThatClassname()).scripts().returning(THAT)).annotateOverride();

        return definition;
    }

    private JavaElemMethod doConvert(JavaCodeBlockAddr<JavaElemMethod> scripts) {
        scripts.returning("{} == null ? null : unsafeCopy({}, new {}())",

            THIS, THIS, Import.nameOf(getThatClassname()));
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
                PropertyMethodDeclared getter = thisProperty.getOriginGetterDeclared();
                if (setter != null && !doMappingOnConversion(scripts, setter, getter)) {
                    doMappingUnsafeCopy(scripts, setter, getter);
                }
            }
        }
        return scripts.end();
    }

    private void doMappingUnsafeCopy(
        JavaCodeBlockAddr<JavaElemMethod> scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getParameterAt(0).getActualType();
        if (STRING_CLASS.equals(setterActualType)) {
            doMapping2String(scripts, setter, getter);
        } else if (Test2.isPrimitiveNumberClass(setterActualType)) {
            doMapping2PrimitiveNumber(scripts, setter, getter, setterActualType);
        } else if (Test2.isWrappedNumberClass(setterActualType)) {
            String setterSimpleType = String2.replaceAll(setterActualType, JAVA_DOT_LANG_DOT, "");
            doMapping2WrappedNumber(scripts, setter, getter, setterSimpleType.toLowerCase());
        } else if (Test2.isEnumClass(setterActualType)) {
            doMappingToEnum(scripts, setter, getter);
        } else if (BIG_DECIMAL_CLASS.equals(setterActualType)) {
            doMapping2BigDecimal(scripts, setter, getter);
        } else if (BIG_INTEGER_CLASS.equals(setterActualType)) {
            doMapping2BigInteger(scripts, setter, getter);
        } else {
            doMapping4Default(scripts, setter, getter);
        }
    }

    private void doMapping2BigInteger(
        JavaCodeBlockAddr<JavaElemMethod> scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        final String longPrimitiveClass = "long";
        String getterActualType = getter.getPropertyActualType();
        if (Test2.isPrimitiveNumberClass(getterActualType)) {
            if (Test2.isPrimitiveNumberSubtypeOf(getterActualType, longPrimitiveClass) ||
                longPrimitiveClass.equals(getterActualType)) {
                scripts.scriptOf("{}.{}({}.valueOf({}.{}()))", THAT, setter.getMethodName(),

                    Import.of(BigInteger.class), THIS, getter.getMethodName());
            } else {
                scripts.scriptOf("{}.{}({}.valueOf((long) {}.{}()))", THAT, setter.getMethodName(),

                    Import.of(BigInteger.class), THIS, getter.getMethodName());
            }
        } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}.longValue()))", THAT,

                setter.getMethodName(), var, Import.of(BigInteger.class), var);
        } else if (STRING_CLASS.equals(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}(new {}({}))",

                THAT, setter.getMethodName(), Import.of(BigInteger.class), var);
            scripts.onElse().scriptOf("{}.{}(null)", THAT, setter.getMethodName());
        }
    }

    /**
     * 支持类型: {@link BigDecimal}{@link BigInteger}
     *
     * @param scripts
     * @param setter
     * @param getter
     */
    private void doMapping2BigDecimal(
        JavaCodeBlockAddr<JavaElemMethod> scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();
        if (Test2.isPrimitiveNumberClass(getterActualType)) {
            scripts.scriptOf("{}.{}({}.valueOf({}.{}()))", THAT, setter.getMethodName(),

                Import.of(BigDecimal.class), THIS, getter.getMethodName());
        } else if (Test2.isWrappedNumberClass(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}))", THAT,

                setter.getMethodName(), var, Import.nameOf(setterActualType), var);
        } else if (STRING_CLASS.equals(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}(new {}({}))",

                THAT, setter.getMethodName(), Import.of(BigDecimal.class), var);
            scripts.onElse().scriptOf("{}.{}(null)", THAT, setter.getMethodName());
        } else if (BIG_INTEGER_CLASS.equals(getterActualType)

            || Objects.equals(getterActualType, "char[]")) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : new {}({}))", THAT,

                setter.getMethodName(), var, Import.of(BigDecimal.class), var);
        } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}.doubleValue()))", THAT,

                setter.getMethodName(), var, Import.of(BigDecimal.class), var);
        }
    }

    private void doMapping2PrimitiveNumber(
        JavaCodeBlockAddr<JavaElemMethod> scripts,
        PropertyMethodDeclared setter,
        PropertyMethodDeclared getter,
        String setterPrimitiveType
    ) {
        String getterActualType = getter.getPropertyActualType();
        if (Test2.isPrimitiveNumberSubtypeOf(getterActualType, setterPrimitiveType)) {
            scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
        } else if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, getterActualType)) {
            scripts.scriptOf("{}.{}(({}) {}.{}())", THAT, setter.getMethodName(),

                setterPrimitiveType, THIS, getter.getMethodName());
        } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.onIfNotNull(var).scriptOf("{}.{}({}.{}Value())",

                THAT, setter.getMethodName(), var, setterPrimitiveType);
        } else if (STRING_CLASS.equals(getterActualType)) {
            final String capitalizedSetterPrimitiveType = String2.capitalize(setterPrimitiveType);
            String setterWrappedClass = JAVA_DOT_LANG_DOT +
                (capitalizedSetterPrimitiveType.equals(INT_CAPITALIZED) ? "Integer" : capitalizedSetterPrimitiveType);
            String var = defineGetterValueVar(scripts, getter);
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parse{}({}))", THAT, setter.getMethodName(),

                Import.nameOf(setterWrappedClass), capitalizedSetterPrimitiveType, var);
        } else if (Test2.isEnumClass(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, INT_PRIMITIVE_CLASS)) {
                scripts.onIfNotNull(var)
                    .scriptOf("{}.{}(({}) {}.ordinal())", THAT, setter.getMethodName(), setterPrimitiveType, var);
            } else {
                scripts.onIfNotNull(var).scriptOf("{}.{}({}.ordinal())", THAT, setter.getMethodName(), var);
            }
        } else if (isMathNumber(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.onIfNotNull(var).scriptOf("{}.{}({}.{}Value())",

                THAT, setter.getMethodName(), var, setterPrimitiveType);
        }
    }

    private void doMapping2WrappedNumber(
        JavaCodeBlockAddr<JavaElemMethod> scripts,
        PropertyMethodDeclared setter,
        PropertyMethodDeclared getter,
        String lowerCasedSetterSimpleType
    ) {
        String setterPrimitiveType = Objects.equals(lowerCasedSetterSimpleType, "integer") ? "int"
            : lowerCasedSetterSimpleType;
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();
        if (Test2.isPrimitiveNumberSubtypeOf(getterActualType, setterPrimitiveType)) {
            scripts.scriptOf("{}.{}({}.valueOf({}.{}()))", THAT, setter.getMethodName(),

                Import.nameOf(setterActualType), THIS, getter.getMethodName());
        } else if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, getterActualType)) {
            scripts.scriptOf("{}.{}(({}) {}.{}())", THAT, setter.getMethodName(),

                setterPrimitiveType, THIS, getter.getMethodName());
        } else if (Test2.isWrappedNumberClass(getterActualType) || Test2.isSubtypeOf(getterActualType, Number.class)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.{}Value())", THAT,

                setter.getMethodName(), var, var, setterPrimitiveType);
        } else if (STRING_CLASS.equals(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null || {}.length() == 0 ? null : {}.valueOf({}))", THAT,

                setter.getMethodName(), var, var, Import.nameOf(setterActualType), var);
        } else if (Test2.isEnumClass(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, INT_PRIMITIVE_CLASS)) {
                scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf(({}) {}.ordinal()))", THAT,

                    setter.getMethodName(), var, Import.nameOf(setterActualType), setterPrimitiveType, var);
            } else {
                scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}.ordinal()))", THAT,

                    setter.getMethodName(), var, Import.nameOf(setterActualType), var);
            }
        } else if (isMathNumber(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.{}Value())", THAT,

                setter.getMethodName(), var, var, setterPrimitiveType);
        }
    }

    private void doMapping4Default(
        JavaCodeBlockAddr<JavaElemMethod> scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();

        if (Test2.hasGeneric(setterActualType) || Test2.hasGeneric(getterActualType)) {
            // 有泛型又不存在完全对应的情况不做处理
            return;
        }
        if (Test2.isSubtypeOf(getterActualType, setterActualType)) {
            scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
        } else if (Test2.isPrimitiveNumberSubtypeOf(getterActualType, setterActualType)) {
            scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
        } else if (Test2.isPrimitiveNumberSubtypeOf(setterActualType, getterActualType)) {
            scripts.scriptOf("{}.{}(({}) {}.{}())", THAT, setter.getMethodName(),

                setterActualType, THIS, getter.getMethodName());
        }
    }

    private boolean doMappingOnConversion(
        JavaCodeBlockAddr<JavaElemMethod> scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();
        Conversion conversion = Conversion.findMatchedConversion(getterActualType, setterActualType);
        if (conversion != null) {
            if (Test2.isPrimitiveClass(getterActualType)) {
                scripts.scriptOf("{}.{}({}.{}({}.{}()))",
                    THAT,
                    setter.getMethodName(),
                    Import.nameOf(conversion.getConvertClass()),
                    conversion.getConvertMethodName(),
                    THIS,
                    getter.getMethodName());
            } else if (Test2.isPrimitiveClass(setterActualType)) {
                String var = defineGetterValueVar(scripts, getter);
                scripts.onIfNotNull(var).scriptOf("{}.{}({}.{}({}))", THAT, setter.getMethodName(),

                    Import.nameOf(conversion.getConvertClass()), conversion.getConvertMethodName(), var);
            } else {
                String var = defineGetterValueVar(scripts, getter);
                scripts.scriptOf("{}.{}({} == null ? null : {}.{}({}))", THAT, setter.getMethodName(), var,

                    Import.nameOf(conversion.getConvertClass()), conversion.getConvertMethodName(), var);
            }
            return true;
        }
        return false;
    }

    private void doMappingToEnum(
        JavaCodeBlockAddr<JavaElemMethod> scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();

        if (Test2.isSubtypeOf(getterActualType, String.class)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}))", THAT,

                setter.getMethodName(), var, Import.nameOf(setterActualType), var);
        } else if (Test2.isPrimitiveNumberClass(getterActualType)) {
            String constVar = defineEnumValues(scripts, setterActualType);
            if (Test2.isPrimitiveNumberSubtypeOf(getterActualType, "long")) {
                scripts.scriptOf("{}.{}({}[{}.{}()])", THAT,

                    setter.getMethodName(), constVar, THIS, getter.getMethodName());
            } else {
                scripts.scriptOf("{}.{}({}[(int) {}.{}()])", THAT,

                    setter.getMethodName(), constVar, THIS, getter.getMethodName());
            }
        } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
            String getterVar = defineGetterValueVar(scripts, getter);
            String constVar = defineEnumValues(scripts, setterActualType);
            String getterSimpleClass = getterActualType.replaceFirst(JAVA_DOT_LANG_DOT, "");
            String getterPrimitiveClass = "Integer".equals(getterSimpleClass) ? INT_PRIMITIVE_CLASS
                : getterSimpleClass.toLowerCase();
            if (Test2.isPrimitiveNumberSubtypeOf(getterPrimitiveClass, "long")) {
                scripts.scriptOf("{}.{}({} == null ? null : {}[{}])", THAT,

                    setter.getMethodName(), getterVar, constVar, getterVar);
            } else {
                scripts.scriptOf("{}.{}({} == null ? null : {}[{}.intValue()])", THAT,

                    setter.getMethodName(), getterVar, constVar, getterVar);
            }
        } else if (Test2.isSubtypeOf(getterActualType, CharSequence.class)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}.toString()))", THAT,

                setter.getMethodName(), var, Import.nameOf(setterActualType), var);
        }
    }

    private void doMapping2String(
        JavaCodeBlockAddr<JavaElemMethod> scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String getterActualType = getter.getPropertyActualType();
        if (STRING_CLASS.equals(getterActualType)) {
            scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
        } else if (Test2.isPrimitiveClass(getterActualType)) {
            scripts.scriptOf("{}.{}({}.valueOf({}.{}()))", THAT,

                setter.getMethodName(), Import.STRING, THIS, getter.getMethodName());
        } else if (Test2.isEnumClass(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.name())", THAT, setter.getMethodName(), var, var);
        } else {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.toString())", THAT, setter.getMethodName(), var, var);
        }
    }

    private String defineGetterValueVar(JavaCodeBlockAddr<JavaElemMethod> scripts, PropertyMethodDeclared getter) {
        String var = scripts.varsHelper().next();
        String getterActualType = getter.getPropertyActualType();
        scripts.scriptOf("{} {} = {}.{}()", Import.nameOf(getterActualType), var, THIS, getter.getMethodName());
        return var;
    }

    private String defineEnumValues(JavaCodeBlockAddr<JavaElemMethod> scripts, String enumClassname) {
        VarSupplier<JavaElemField> fieldsSupplier = scripts.fieldsHelper();
        String constVar = fieldsSupplier.nextConstVar(enumClassname);
        fieldsSupplier.declareField(constVar, "{}[]", enumClassname)
            .assign()
            .valueOfFormatted("{}.values()", Import.nameOf(enumClassname))
            .end()
            .modifierWithAll(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        return constVar;
    }
}
