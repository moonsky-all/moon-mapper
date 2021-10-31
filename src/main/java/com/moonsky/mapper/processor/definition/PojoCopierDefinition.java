package com.moonsky.mapper.processor.definition;

import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.mapper.processor.holder.MapperHolders;
import com.moonsky.mapper.util.Formatter;
import com.moonsky.mapper.util.NamingStrategy;
import com.moonsky.processor.processing.declared.PojoDeclared;
import com.moonsky.processor.processing.declared.PropertyDeclared;
import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.filer.JavaFileDetails;
import com.moonsky.processor.processing.filer.JavaSupplier;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.generate.ElemMethod;
import com.moonsky.processor.processing.generate.JavaFileClassDetails;
import com.moonsky.processor.processing.util.AliasConstant2;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.String2;
import com.moonsky.processor.processing.util.Test2;

import javax.lang.model.element.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
public class PojoCopierDefinition extends PojoBaseDefinition implements JavaSupplier {

    private static final String INT_PRIMITIVE_CLASS = int.class.getCanonicalName();
    // private static final String STRING_CLASS = String.class.getCanonicalName();
    // private static final String INT_CAPITALIZED = String2.capitalize(int.class.getCanonicalName());
    private static final String JAVA_DOT_LANG_DOT = "java.lang.";
    // private static final String BIG_DECIMAL_CLASS = BigDecimal.class.getCanonicalName();
    // private static final String BIG_INTEGER_CLASS = BigInteger.class.getCanonicalName();
    private static final Imported<Class<Formatter>> FORMATTER_IMPORTED = Imported.of(Formatter.class);

    // private static boolean isMathNumberClass(String fullClassname) {
    //     return BIG_DECIMAL_CLASS.equals(fullClassname) || BIG_INTEGER_CLASS.equals(fullClassname);
    // }

    // private static boolean isStringClass(String classname) {
    //     return STRING_CLASS.equals(classname);
    // }

    private final String simpleName;
    private final String classname;

    public PojoCopierDefinition(
        MapperHolders holders, MapperNaming namingAttributes, PojoDeclared thisDeclared, PojoDeclared thatDeclared
    ) {
        super(holders, thisDeclared, thatDeclared);
        String thisClass = getThisDeclared().getThisSimpleName();
        String thatClass = getThatDeclared().getThisSimpleName();
        this.simpleName = NamingStrategy.copierOf(namingAttributes, thisClass, thatClass);
        this.classname = getPackageName() + '.' + simpleName;
    }

    public String getClassname() {return classname;}

    public String getSimpleName() {return simpleName;}

    @Override
    public JavaFileDetails get() {
        String packageName = getPackageName();
        String classname = getSimpleName();
        JavaFileClassDetails definition = new JavaFileClassDetails(packageName, classname);
        definition.impls()
            .implement("{}<{}, {}>", AliasConstant2.BeanCopier_ClassName, getThisClassname(), getThatClassname());
        definition.annotateComponent().annotateCopierImplGenerated();

        definition.fields()
            .declareField(AliasConstant2.CONST, definition.getClassname())
            .assign()
            .valueOfFormatted("new {}()", Imported.nameOf(definition.getClassname()))
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

    private ElemMethod doConvert(CodeMethodBlockAddr scripts) {
        return scripts.returning("{} == null ? null : unsafeCopy({}, new {}())",

            THIS, THIS, Imported.nameOf(getThatClassname())).end();
    }

    private ElemMethod doUnsafeCopy(CodeMethodBlockAddr scripts) {
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

            // 基于自定义 setter 的转换器
            if (String2.isNotBlank(setterScript)) {
                scripts.scriptOf(setterScript);
                continue;
            }

            PropertyMethodDeclared setter = thatProperty.getOnlySetterMethod();
            PropertyMethodDeclared getter = thisProperty.getOriginGetterDeclared();
            if (getter == null || setter == null) {
                // 没有 getter 或 setter
                continue;
            }
            if (doMappingOnConversion(scripts, setter, getter)) {
                // 使用转换器
                continue;
            }
            if (doMappingUnsafeCopy(scripts, setter, getter)) {
                // 默认规则
                continue;
            }
            // 无法映射
            doNoneMapping(scripts, thatProperty, thisProperty);
        }
        return scripts.end();
    }

    private boolean doMappingUnsafeCopy(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        boolean mapped;
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();
        // if (isStringClass(setterActualType)) {
        //     // mapped = doMapping2String(scripts, setter, getter);
        //     // } else if (Test2.isPrimitiveNumberClass(setterActualType)) {
        //     //     mapped = doMapping2PrimitiveNumber(scripts, setter, getter, setterActualType);
        //     // } else if (Test2.isWrappedNumberClass(setterActualType)) {
        //     //     String setterSimpleType = String2.replaceAll(setterActualType, JAVA_DOT_LANG_DOT, "");
        //     //     mapped = doMapping2WrappedNumber(scripts, setter, getter, setterSimpleType.toLowerCase());
        // } else
        if (Test2.isEnumClass(setterActualType)) {
            mapped = doMappingToEnum(scripts, setter, getter);
            // } else if (BIG_DECIMAL_CLASS.equals(setterActualType)) {
            //     // mapped = doMapping2BigDecimal(scripts, setter, getter);
            //     mapped = false;
            // } else if (BIG_INTEGER_CLASS.equals(setterActualType)) {
            //     // mapped = doMapping2BigInteger(scripts, setter, getter);
            //     mapped = false;
        } else if (Test2.isSubtypeOf(getterActualType, setterActualType)) {
            scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
            mapped = true;
            // } else if (Test2.isPrimitiveNumberSubtypeOf(getterActualType, setterActualType)) {
            //     // scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
            //     mapped = true;
            // } else if (Test2.isPrimitiveNumberSubtypeOf(setterActualType, getterActualType)) {
            //     // scripts.scriptOf("{}.{}(({}) {}.{}())", THAT, setter.getMethodName(),
            //     //
            //     //     setterActualType, THIS, getter.getMethodName());
            //     mapped = true;
            // } else if (Test2.isPrimitiveBoolClass(setterActualType)) {
            //     if (Test2.isTypeof(getterActualType, Boolean.class)) {
            //         // String var = defineGetterValueVar(scripts, getter);
            //         // scripts.scriptOf("{}.{}({} != null && {})", THAT, setter.getMethodName(), var, var);
            //         mapped = true;
            //     } else {
            //         // mapped = doMappingString2Boolean(scripts, setter, getter);
            //         mapped = true;
            //     }
            // } else if (Test2.isTypeof(setterActualType, Boolean.class)) {
            //     if (Test2.isTypeof(getterActualType, boolean.class)) {
            //         // scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
            //         mapped = true;
            //     } else {
            //         // mapped = doMappingString2Boolean(scripts, setter, getter);
            //         mapped = true;
            //     }
            // } else if (Test2.isPrimitiveCharClass(setterActualType)) {
            //     if (Test2.isTypeof(getterActualType, Character.class)) {
            //         // String var = defineGetterValueVar(scripts, getter);
            //         // scripts.onIfNotNull(var).scriptOf("{}.{}({})", THAT, setter.getMethodName(), var);
            //         mapped = true;
            //     } else {
            //         mapped = false;
            //     }
            // } else if (Test2.isTypeof(setterActualType, Character.class)) {
            //     if (Test2.isPrimitiveCharClass(getterActualType)) {
            //         // scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
            //         mapped = true;
            //     } else {
            //         mapped = false;
            //     }
        } else {
            // mapped = doMapping2Date(scripts, setter, getter) || doMappingJoda1x4Classes(scripts, setter, getter);
            return false;
        }
        return mapped;
    }

    // private boolean doMapping2Date(
    //     CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    // ) {
    //     // String setterActualType = setter.getPropertyActualType();
    //     // String getterActualType = getter.getPropertyActualType();
    //     // if (isStringClass(getterActualType)) {
    //     //     if (Test2.isTypeofAny(setterActualType, LocalDateTime.class, ZonedDateTime.class,
    //     //
    //     //         OffsetDateTime.class, LocalDate.class, LocalTime.class, Year.class,
    //     //
    //     //         OffsetTime.class, MonthDay.class, YearMonth.class)) {
    //     //         // String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
    //     //         // String var = defineGetterValueVar(scripts, getter);
    //     //         // if (String2.isBlank(pattern)) {
    //     //         //     onNull(scripts, setter, var).or("{}.parse({})", Imported.nameOf(setterActualType), var);
    //     //         // } else {
    //     //         //     String formatConst = defineJdk8DateTimeFormatter(scripts, pattern);
    //     //         //     onNull(scripts, setter, var).or("{}.parse({}, {})",
    //     //         //         Imported.nameOf(setterActualType),
    //     //         //         var,
    //     //         //         formatConst);
    //     //         // }
    //     //     // } else if (Test2.isTypeof(setterActualType, Instant.class)) {
    //     //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     //     // onNull(scripts, setter, var).or("{}.parse({})", Imported.of(Instant.class), var);
    //     //     // } else if (Test2.isTypeof(setterActualType, Date.class)) {
    //     //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     //     // String pattern = getFormatOrDefaultIfBlank(setter, getter, AliasConstant2.PATTERN_DATETIME);
    //     //     //     // onNull(scripts, setter, var).or("{}.parse({}, {})", FORMATTER_IMPORTED, var, Stringify.of(pattern));
    //     //     // } else if (Test2.isTypeof(setterActualType, Calendar.class)) {
    //     //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     //     // String pattern = getFormatOrDefaultIfBlank(setter, getter, AliasConstant2.PATTERN_DATETIME);
    //     //     //     // onNull(scripts, setter, var).or("{}.parseCalendar({}, {})",
    //     //     //     //     FORMATTER_IMPORTED,
    //     //     //     //     var,
    //     //     //     //     Stringify.of(pattern));
    //     //     // } else if (Test2.isTypeof(setterActualType, java.sql.Date.class)) {
    //     //     //     // doMappingString2JavaSQLDate(scripts, setter, getter, AliasConstant2.PATTERN_DATE);
    //     //     // } else if (Test2.isTypeof(setterActualType, java.sql.Time.class)) {
    //     //     //     // doMappingString2JavaSQLDate(scripts, setter, getter, AliasConstant2.PATTERN_TIME);
    //     //     // } else if (Test2.isTypeof(setterActualType, java.sql.Timestamp.class)) {
    //     //     //     // doMappingString2JavaSQLDate(scripts, setter, getter, AliasConstant2.PATTERN_DATETIME);
    //     //     } else if (Test2.isImportedAndJodaDateClass(setterActualType)) {
    //     //         if (Import2.JODA_TIME_2X && isAfterJoda2x0(setterActualType)) {
    //     //             // doMappingString2Joda2xNormalDate(scripts, setter, getter, setterActualType);
    //     //         // } else if (Import2.JODA_TIME_1X0 && isAfterJoda1x0(setterActualType)) {
    //     //             // doMappingString2Joda1xNormalDate(scripts, setter, getter, setterActualType);
    //     //         // } else if (Import2.JODA_TIME_1X3 && isAfterJoda1x3(setterActualType)) {
    //     //             // doMappingString2Joda1xNormalDate(scripts, setter, getter, setterActualType);
    //     //         // } else if (Import2.JODA_TIME_1X4 && isAfterJoda1x4(setterActualType)) {
    //     //         //     String var = defineGetterValueVar(scripts, getter);
    //     //         //     onNull(scripts, setter, var).or("{}.parse{}({})",
    //     //         //         Imported.nameOf(setterActualType),
    //     //         //         Element2.getSimpleName(setterActualType),
    //     //         //         var);
    //     //         } else {
    //     //             return false;
    //     //         }
    //     //     } else {
    //     //         return false;
    //     //     }
    //     //     return true;
    //     // }
    //     return false;
    // }

    // private boolean doMappingJoda1x4Classes(
    //     CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    // ) {
    //     String setterActualType = setter.getPropertyActualType();
    //     String getterActualType = getter.getPropertyActualType();
    //     if (Import2.JODA_TIME_1X4 && isAfterJoda1x4(setterActualType)) {
    //         if (Test2.isSubtypeOf(getterActualType, CharSequence.class)) {
    //             // String var = defineGetterValueVar(scripts, getter);
    //             // onNull(scripts, setter, var).or("{}.parse{}({}.toString())",
    //             //     Imported.nameOf(setterActualType),
    //             //     Element2.getSimpleName(setterActualType),
    //             //     var);
    //         } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
    //             // String var = defineGetterValueVar(scripts, getter);
    //             // onNull(scripts, setter, var).or("{}.{}({}.intValue())",
    //             //     Imported.nameOf(setterActualType),
    //             //     Element2.getSimpleName(setterActualType).toLowerCase(),
    //             //     var);
    //         } else if (Test2.isPrimitiveNumberClass(getterActualType)) {
    //             // String onLtLong = "{}.{}({}.{}({}.{}()))";
    //             // String onGeLong = "{}.{}({}.{}((int) {}.{}()))";
    //             // boolean isLtLong = Test2.isPrimitiveNumberSubtypeOf(getterActualType, "long");
    //             // scripts.scriptOf(isLtLong ? onLtLong : onGeLong,
    //             //
    //             //     THAT, setter.getMethodName(), Imported.nameOf(setterActualType),
    //             //
    //             //     Element2.getSimpleName(setterActualType).toLowerCase(),
    //             //
    //             //     THIS, getter.getMethodName());
    //         } else {
    //             return false;
    //         }
    //         return true;
    //     }
    //     return false;
    // }

    // private void doMappingString2Joda1xNormalDate(
    //     CodeMethodBlockAddr scripts,
    //     PropertyMethodDeclared setter,
    //     PropertyMethodDeclared getter,
    //     String setterActualType
    // ) {
    //     String var = defineGetterValueVar(scripts, getter);
    //     String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
    //     String setterSimpleType = Element2.getSimpleName(setterActualType);
    //     if (String2.isBlank(pattern)) {
    //         VarSupplier<ElemField> fieldHelper = scripts.fieldsHelper();
    //         String formatterName = AliasConstant2.Joda_DateTimeFormatter_ClassName;
    //         String formatterKey = String2.keyOf(formatterName, setterActualType);
    //         String formatterConst = fieldHelper.nextConstVar(formatterKey);
    //         if (!fieldHelper.contains(formatterConst)) {
    //             String method = "dateTime";
    //             if (Import2.JODA_TIME_1X3) {
    //                 if (Test2.isTypeof(setterActualType, AliasConstant2.Joda_LocalDate_ClassName)) {
    //                     method = "date";
    //                 } else if (Test2.isTypeof(setterActualType, AliasConstant2.Joda_LocalTime_ClassName)) {
    //                     method = "time";
    //                 }
    //             }
    //             fieldHelper.declareField(formatterConst, formatterName)
    //                 .assign()
    //                 .valueOfFormatted("{}.{}()",
    //                     Imported.nameOf(AliasConstant2.Joda_ISODateTimeFormat_ClassName),
    //                     method);
    //         }
    //         onNull(scripts, setter, var).or("{}.parse{}({})", formatterConst, setterSimpleType, var);
    //     } else {
    //         String formatConst = defineJodaDateTimeFormatter(scripts, pattern);
    //         onNull(scripts, setter, var).or("{}.parse{}({})", formatConst, setterSimpleType, var);
    //     }
    // }

    // private void doMappingString2Joda2xNormalDate(
    //     CodeMethodBlockAddr scripts,
    //     PropertyMethodDeclared setter,
    //     PropertyMethodDeclared getter,
    //     String setterActualType
    // ) {
    //     // String var = defineGetterValueVar(scripts, getter);
    //     // String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
    //     // Imported<String> setterImported = Imported.nameOf(setterActualType);
    //     // if (String2.isBlank(pattern)) {
    //     //     onNull(scripts, setter, var).or("{}.parse({})", setterImported, var);
    //     // } else {
    //     //     String formatConst = defineJodaDateTimeFormatter(scripts, pattern);
    //     //     onNull(scripts, setter, var).or("{}.parse({}, {})", setterImported, var, formatConst);
    //     // }
    // }

    // private void doMappingString2JavaSQLDate(
    //     CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter, String defaultPattern
    // ) {
    //     String pattern = getFormatOrDefaultIfBlank(setter, getter, defaultPattern);
    //     String var = defineGetterValueVar(scripts, getter);
    //     onNull(scripts, setter, var).or("new {}({}.parse({}, {}).getTime())",//
    //         Imported.nameOf(setter.getPropertyActualType()),//
    //         FORMATTER_IMPORTED, var, Stringify.of(pattern));
    // }

    // private boolean doMapping2BigInteger(
    //     CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    // ) {
    //     // String getterActualType = getter.getPropertyActualType();
    //     // if (Test2.isPrimitiveNumberClass(getterActualType)) {
    //     //     // String cast = Test2.isLeLongClass(getterActualType) ? null : "(long)";
    //     //     // setValueOf(scripts, setter, doGetterVal(getter, cast));
    //     // } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // onNull(scripts, setter, var).or("{}.valueOf({}.longValue())", Imported.BIG_INTEGER, var);
    //     // } else if (isStringClass(getterActualType)) {
    //     //     // final String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // if (String2.isBlank(pattern)) {
    //     //     //     scripts.onStringIfNotEmpty(var).scriptOf("{}.{}(new {}({}))",
    //     //     //
    //     //     //         THAT, setter.getMethodName(), Imported.BIG_INTEGER, var);
    //     //     // } else {
    //     //     //     String template = "{}.{}({}.valueOf({}.parseNumber({}, {}).longValue()))";
    //     //     //     scripts.onStringIfNotEmpty(var).scriptOf(template, THAT, setter.getMethodName(),
    //     //     //
    //     //     //         Imported.BIG_INTEGER, FORMATTER_IMPORTED, var, Stringify.of(pattern));
    //     //     // }
    //     //     // scripts.onElse().scriptOf("{}.{}(null)", THAT, setter.getMethodName());
    //     // } else {
    //     //     return false;
    //     // }
    //     return true;
    // }

    /**
     * 支持类型: {@link BigDecimal}{@link BigInteger}
     *
     * @param scripts
     * @param setter
     * @param getter
     */
    // private boolean doMapping2BigDecimal(
    //     CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    // ) {
    //     // String getterActualType = getter.getPropertyActualType();
    //     // if (Test2.isPrimitiveNumberClass(getterActualType)) {
    //     //     // TODO: 已匹配指定方式
    //     //     // setValueOf(scripts, setter, doGetterVal(getter));
    //     // } else if (Test2.isWrappedNumberClass(getterActualType)) {
    //     //     // TODO: 已匹配指定方式
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // onNull(scripts, setter, var).or("{}.valueOf({})", Imported.BIG_DECIMAL, var);
    //     // } else if (isStringClass(getterActualType)) {
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
    //     //     // if (String2.isBlank(pattern)) {
    //     //     //     scripts.onStringIfNotEmpty(var).scriptOf("{}.{}(new {}({}))",
    //     //     //
    //     //     //         THAT, setter.getMethodName(), Imported.BIG_DECIMAL, var);
    //     //     // } else {
    //     //     //     scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parseBigDecimal({}, {}))", THAT,
    //     //     //
    //     //     //         setter.getMethodName(), FORMATTER_IMPORTED, var, Stringify.of(pattern));
    //     //     // }
    //     //     // scripts.onElseNull(THAT, setter.getMethodName());
    //     // } else if (BIG_INTEGER_CLASS.equals(getterActualType)
    //     //
    //     //     || Objects.equals(getterActualType, "char[]")) {
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // onNull(scripts, setter, var).or("new {}({})", Imported.BIG_DECIMAL, var);
    //     // } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // onNull(scripts, setter, var).or("{}.valueOf({}.doubleValue())", Imported.BIG_DECIMAL, var);
    //     // } else {
    //     //     return false;
    //     // }
    //     return true;
    // }

    // private boolean doMapping2PrimitiveNumber(
    //     CodeMethodBlockAddr scripts,
    //     PropertyMethodDeclared setter,
    //     PropertyMethodDeclared getter,
    //     String setterPrimitiveType
    // ) {
    //     // String var;
    //     // String getterActualType = getter.getPropertyActualType();
    //     // if (Objects.equals(getterActualType, setterPrimitiveType)) {
    //     //     // scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
    //     // } else if (Test2.isPrimitiveNumberSubtypeOf(getterActualType, setterPrimitiveType)) {
    //     //     // scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
    //     // } else if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, getterActualType)) {
    //     //     // scripts.scriptOf("{}.{}(({}) {}.{}())", THAT, setter.getMethodName(),
    //     //     //
    //     //     //     setterPrimitiveType, THIS, getter.getMethodName());
    //     // } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
    //     //     // var = defineGetterValueVar(scripts, getter);
    //     //     // scripts.onIfNotNull(var).scriptOf("{}.{}({}.{}Value())",
    //     //     //
    //     //     //     THAT, setter.getMethodName(), var, setterPrimitiveType);
    //     // } else if (isStringClass(getterActualType)) {
    //     //     // final String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
    //     //     // final String capitalizedSetterPrimitiveType = String2.capitalize(setterPrimitiveType);
    //     //     // String setterWrappedClass = JAVA_DOT_LANG_DOT + (capitalizedSetterPrimitiveType.equals(INT_CAPITALIZED) ? "Integer" : capitalizedSetterPrimitiveType);
    //     //     // var = defineGetterValueVar(scripts, getter);
    //     //     // if (String2.isBlank(pattern)) {
    //     //     //     scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parse{}({}))", THAT,
    //     //     //
    //     //     //         setter.getMethodName(), Imported.nameOf(setterWrappedClass),
    //     //     //
    //     //     //         capitalizedSetterPrimitiveType, var);
    //     //     // } else {
    //     //     //     scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parseNumber({}, {}).{}Value())",
    //     //     //
    //     //     //         THAT, setter.getMethodName(), FORMATTER_IMPORTED,
    //     //     //
    //     //     //         var, Stringify.of(pattern), setterPrimitiveType);
    //     //     // }
    //     // } else if (Test2.isEnumClass(getterActualType)) {
    //     //     // var = defineGetterValueVar(scripts, getter);
    //     //     // if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, INT_PRIMITIVE_CLASS)) {
    //     //     //     scripts.onIfNotNull(var)
    //     //     //         .scriptOf("{}.{}(({}) {}.ordinal())", THAT, setter.getMethodName(), setterPrimitiveType, var);
    //     //     // } else {
    //     //     //     scripts.onIfNotNull(var).scriptOf("{}.{}({}.ordinal())", THAT, setter.getMethodName(), var);
    //     //     // }
    //     // } else if (isMathNumberClass(getterActualType)) {
    //     //     // var = defineGetterValueVar(scripts, getter);
    //     //     // scripts.onIfNotNull(var).scriptOf("{}.{}({}.{}Value())",
    //     //     //
    //     //     //     THAT, setter.getMethodName(), var, setterPrimitiveType);
    //     // } else
    //     // if (Import2.JODA_TIME_1X4 && isAfterJoda1x4(getterActualType)) {
    //     //     var = defineGetterValueVar(scripts, getter);
    //     //     if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, "int")) {
    //     //         scripts.onIfNotNull(var).scriptOf("{}.{}(({}) {}.get{}())",
    //     //
    //     //             THAT, setter.getMethodName(), setterPrimitiveType, var, Element2.getSimpleName(getterActualType));
    //     //     } else {
    //     //         scripts.onIfNotNull(var).scriptOf("{}.{}({}.get{}())",
    //     //
    //     //             THAT, setter.getMethodName(), var, Element2.getSimpleName(getterActualType));
    //     //     }
    //     // } else if (Test2.isTypeofAny(getterActualType, Month.class, Year.class)) {
    //     //     var = defineGetterValueVar(scripts, getter);
    //     //     if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, "int")) {
    //     //         scripts.onIfNotNull(var).scriptOf("{}.{}(({}) {}.getValue())",
    //     //
    //     //             THAT, setter.getMethodName(), setterPrimitiveType, var);
    //     //     } else {
    //     //         scripts.onIfNotNull(var).scriptOf("{}.{}({}.getValue())",
    //     //
    //     //             THAT, setter.getMethodName(), var);
    //     //     }
    //     // } else {
    //     //     return false;
    //     // }
    //     return true;
    // }

    // private boolean doMapping2WrappedNumber(
    //     CodeMethodBlockAddr scripts,
    //     PropertyMethodDeclared setter,
    //     PropertyMethodDeclared getter,
    //     String lowerCasedSetterSimpleType
    // ) {
    //     // String setterPrimitiveType = Objects.equals(lowerCasedSetterSimpleType,
    //     //     "integer") ? "int" : lowerCasedSetterSimpleType;
    //     // String setterActualType = setter.getPropertyActualType();
    //     // String getterActualType = getter.getPropertyActualType();
    //     // if (Objects.equals(getterActualType, setterPrimitiveType)) {
    //     //     // scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
    //     // } else if (Test2.isPrimitiveNumberSubtypeOf(getterActualType, setterPrimitiveType)) {
    //     //     // scripts.scriptOf("{}.{}({}.valueOf({}.{}()))", THAT, setter.getMethodName(),
    //     //     //
    //     //     //     Imported.nameOf(setterActualType), THIS, getter.getMethodName());
    //     // } else if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, getterActualType)) {
    //     //     // scripts.scriptOf("{}.{}(({}) {}.{}())", THAT, setter.getMethodName(),
    //     //     //
    //     //     //     setterPrimitiveType, THIS, getter.getMethodName());
    //     // } else if (Test2.isWrappedNumberClass(getterActualType) || Test2.isSubtypeOf(getterActualType, Number.class)) {
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // onNull(scripts, setter, var).or("{}.{}Value()", var, setterPrimitiveType);
    //     // } else if (isStringClass(getterActualType)) {
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
    //     //     // if (String2.isBlank(pattern)) {
    //     //     //     scripts.onStringIfNotEmpty(var)
    //     //     //         .scriptOf("{}.{}({}.valueOf({}))",
    //     //     //             THAT,
    //     //     //             setter.getMethodName(),
    //     //     //             Imported.nameOf(setterActualType),
    //     //     //             var);
    //     //     // } else {
    //     //     //     scripts.onStringIfNotEmpty(var)
    //     //     //         .scriptOf("{}.{}({}.parseNumber({}, {}).{}Value())",
    //     //     //             THAT,
    //     //     //             setter.getMethodName(),
    //     //     //             FORMATTER_IMPORTED,
    //     //     //             var,
    //     //     //             Stringify.of(pattern),
    //     //     //             setterPrimitiveType);
    //     //     // }
    //     //     // scripts.onElse().scriptOf("{}.{}(null)", THAT, setter.getMethodName());
    //     // } else if (Test2.isEnumClass(getterActualType)) {
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // boolean isSubtypeOfInt = isSubtypeOfInt(setterPrimitiveType);
    //     //     // if (isSubtypeOfInt) {
    //     //     //     onNull(scripts, setter, var).or("{}.valueOf(({}) {}.ordinal())",
    //     //     //         Imported.nameOf(setterActualType),
    //     //     //         setterPrimitiveType,
    //     //     //         var);
    //     //     // } else {
    //     //     //     onNull(scripts, setter, var).or("{}.valueOf({}.ordinal())", Imported.nameOf(setterActualType), var);
    //     //     // }
    //     // } else if (isMathNumberClass(getterActualType)) {
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // onNull(scripts, setter, var).or("{}.{}Value()", var, setterPrimitiveType);
    //     // } else
    //     // if (Import2.JODA_TIME_1X4 && isAfterJoda1x4(getterActualType)) {
    //     //     String var = defineGetterValueVar(scripts, getter);
    //     //     if (isSubtypeOfInt(setterPrimitiveType)) {
    //     //         onNull(scripts, setter, var).or("{}.valueOf(({}) {}.get{}())",
    //     //             Imported.nameOf(setterActualType),
    //     //             setterPrimitiveType,
    //     //             var,
    //     //             Element2.getSimpleName(getterActualType));
    //     //     } else {
    //     //         onNull(scripts, setter, var).or("{}.valueOf({}.get{}())",
    //     //             Imported.nameOf(setterActualType),
    //     //             var,
    //     //             Element2.getSimpleName(getterActualType));
    //     //     }
    //     // } else if (Test2.isTypeofAny(getterActualType, Month.class, Year.class)) {
    //     //     String var = defineGetterValueVar(scripts, getter);
    //     //     if (isSubtypeOfInt(setterPrimitiveType)) {
    //     //         onNull(scripts, setter, var).or("{}.valueOf(({}) {}.getValue())",
    //     //             Imported.nameOf(setterActualType),
    //     //             setterPrimitiveType,
    //     //             var);
    //     //     } else {
    //     //         onNull(scripts, setter, var).or("{}.valueOf({}.getValue())", Imported.nameOf(setterActualType), var);
    //     //     }
    //     // } else {
    //     //     return false;
    //     // }
    //     return true;
    // }

    // private boolean doMappingString2Boolean(
    //     CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    // ) {
    //     // String getterActualType = getter.getPropertyActualType();
    //     // if (Test2.isTypeof(getterActualType, String.class)) {
    //     //     // scripts.scriptOf("{}.{}({}.parseBoolean({}.{}()))", THAT,
    //     //     //
    //     //     //     setter.getMethodName(), Imported.of(Boolean.class), THIS, getter.getMethodName());
    //     // } else if (Test2.isSubtypeOf(getterActualType, CharSequence.class)) {
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // scripts.scriptOf("{}.{}({} != null && {}.parseBoolean({}.toString()))",
    //     //     //
    //     //     //     THAT, setter.getMethodName(), var, Imported.of(Boolean.class), var);
    //     // } else {
    //     //     return false;
    //     // }
    //     return true;
    // }
    private boolean doMappingToEnum(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();

        if (Test2.isSubtypeOf(getterActualType, String.class)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}))", THAT,

                setter.getMethodName(), var, Imported.nameOf(setterActualType), var);
        } else if (Test2.isPrimitiveNumberClass(getterActualType)) {
            String constVar = defineEnumValues(scripts, setterActualType);
            if (Test2.isSubtypeOfLong(getterActualType)) {
                scripts.scriptOf("{}.{}({}[{}.{}()])", THAT,

                    setter.getMethodName(), constVar, THIS, getter.getMethodName());
            } else {
                scripts.scriptOf("{}.{}({}[(int) {}.{}()])", THAT,

                    setter.getMethodName(), constVar, THIS, getter.getMethodName());
            }
        } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
            String getterVar = defineGetterValueVar(scripts, getter);
            String constVar = defineEnumValues(scripts, setterActualType);
            String getterSimpleCls = getterActualType.replaceFirst(JAVA_DOT_LANG_DOT, "");
            String getterPrimitiveCls = "Integer".equals(getterSimpleCls) ? INT_PRIMITIVE_CLASS : getterSimpleCls.toLowerCase();
            boolean isSubtypeOfLong = Test2.isSubtypeOfLong(getterPrimitiveCls);
            String valueTemplate = isSubtypeOfLong ? "{}[{}]" : "{}[{}.intValue()]";
            onNull(scripts, setter, getterVar).or(valueTemplate, constVar, getterVar);
        } else if (Test2.isSubtypeOf(getterActualType, CharSequence.class)) {
            String var = defineGetterValueVar(scripts, getter);
            onNull(scripts, setter, var).or("{}.valueOf({}.toString())",
                FORMATTER_IMPORTED,
                Imported.nameOf(setterActualType),
                var);
        } else {
            return false;
        }
        return true;
    }

    // private boolean doMapping2String(
    //     CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    // ) {
    //     // String getterActualType = getter.getPropertyActualType();
    //     // if (isStringClass(getterActualType)) {
    //     //     // scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
    //     // } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
    //     //     // String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
    //     //     // if (String2.isBlank(pattern)) {
    //     //     //     doMapping2SimpleString(scripts, setter, getter);
    //     //     // } else {
    //     //     //     String var = defineGetterValueVar(scripts, getter);
    //     //     //     onNull(scripts, setter, var).or("{}.format({}, {})", FORMATTER_IMPORTED, var, Stringify.of(pattern));
    //     //     // }
    //     // } else if (Test2.isPrimitiveNumberClass(getterActualType)) {
    //     //     // String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
    //     //     // if (String2.isBlank(pattern)) {
    //     //     //     scripts.scriptOf("{}.{}({}.valueOf({}.{}()))",
    //     //     //         THAT,
    //     //     //         setter.getMethodName(),
    //     //     //         Imported.STRING,
    //     //     //         THIS,
    //     //     //         getter.getMethodName());
    //     //     // } else {
    //     //     //     scripts.scriptOf("{}.{}({}.format({}.{}(), {}))",
    //     //     //         THAT,
    //     //     //         setter.getMethodName(),
    //     //     //         FORMATTER_IMPORTED,
    //     //     //         THIS,
    //     //     //         getter.getMethodName(),
    //     //     //         Stringify.of(pattern));
    //     //     // }
    //     // } else if (Test2.isPrimitiveBoolClass(getterActualType) || Test2.isPrimitiveCharClass(getterActualType)) {
    //     //     // scripts.scriptOf("{}.{}({}.valueOf({}.{}()))",
    //     //     //     THAT,
    //     //     //     setter.getMethodName(),
    //     //     //     Imported.STRING,
    //     //     //     THIS,
    //     //     //     getter.getMethodName());
    //     // } else if (Test2.isEnumClass(getterActualType)) {
    //     //     // String var = defineGetterValueVar(scripts, getter);
    //     //     // onNull(scripts, setter, var).or("{}.name()", var);
    //     // } else if (Test2.isSubtypeOf(getterActualType, TemporalAccessor.class)) {
    //     //     // String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
    //     //     // if (String2.isBlank(pattern)) {
    //     //     //     String formatConst = defineJdk8DateTimeFormatter(scripts, pattern);
    //     //     //     String var = defineGetterValueVar(scripts, getter);
    //     //     //     onNull(scripts, setter, var).or("{}.format({})", formatConst, var);
    //     //     // } else {
    //     //     //     doMapping2SimpleString(scripts, setter, getter);
    //     //     // }
    //     // } else
    //     // if (Test2.isImportedAndJodaDateClass(getterActualType)) {
    //     // String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
    //     // if (String2.isBlank(pattern)) {
    //     //     String formatConst = defineJodaDateTimeFormatter(scripts, pattern);
    //     //     String var = defineGetterValueVar(scripts, getter);
    //     //     onNull(scripts, setter, var).or("{}.print({})", formatConst, var);
    //     // } else {
    //     //     doMapping2SimpleString(scripts, setter, getter);
    //     // }
    //     // } else if (Test2.isTypeof(getterActualType, java.sql.Time.class)) {
    //     //     // doMappingUtilDate2String(scripts, setter, getter, AliasConstant2.PATTERN_TIME);
    //     // } else if (Test2.isTypeof(getterActualType, java.sql.Date.class)) {
    //     //     // doMappingUtilDate2String(scripts, setter, getter, AliasConstant2.PATTERN_DATE);
    //     // } else if (Test2.isSubtypeOf(getterActualType, Date.class) || Test2.isSubtypeOf(getterActualType,
    //     //     Calendar.class)) {
    //     //     // doMappingUtilDate2String(scripts, setter, getter, AliasConstant2.PATTERN_DATETIME);
    //     // } else {
    //     //     // doMapping2SimpleString(scripts, setter, getter);
    //     // }
    //     return true;
    // }

    // private static void doMappingUtilDate2String(
    //     CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter, String defaultPattern
    // ) {
    //     Stringify stringify = Stringify.of(getFormatOrDefaultIfBlank(setter, getter, defaultPattern));
    //     String var = defineGetterValueVar(scripts, getter);
    //     onNull(scripts, setter, var).or("{}.format({}, {})", FORMATTER_IMPORTED, var, stringify);
    // }

    // private static void doMapping2SimpleString(
    //     CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    // ) {
    //     String var = defineGetterValueVar(scripts, getter);
    //     onNull(scripts, setter, var).or("{}.toString()", var);
    // }

    private static boolean doMappingOnConversion(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        Conversion conversion = ConversionRegistry.find(getter.getPropertyActualType(), setter.getPropertyActualType());
        if (conversion != null) {
            conversion.doMapping(scripts, getter, setter);
            return true;
        }
        return false;
    }
}
