package com.moonsky.processing.definition;

import com.moonsky.mapper.BeanCopier;
import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.mapper.annotation.Mapping;
import com.moonsky.mapper.util.Formatter;
import com.moonsky.mapper.util.NamingStrategy;
import com.moonsky.processing.converter.Converter;
import com.moonsky.processing.declared.PojoDeclared;
import com.moonsky.processing.declared.PropertyDeclared;
import com.moonsky.processing.declared.PropertyMethodDeclared;
import com.moonsky.processing.generate.*;
import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.processor.JavaDefinition;
import com.moonsky.processing.processor.JavaSupplier;
import com.moonsky.processing.util.*;
import com.moonsky.processing.util.Imported;
import com.moonsky.processing.util.Stringify;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import javax.lang.model.element.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
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

    private static boolean isString(String classname) {
        return STRING_CLASS.equals(classname);
    }

    private final String simpleName;
    private final String classname;

    public PojoCopierDefinition(
        Holders holders, MapperNaming naming, PojoDeclared thisDeclared, PojoDeclared thatDeclared
    ) {
        super(holders, thisDeclared, thatDeclared);
        String thisClass = getThisDeclared().getThisSimpleName();
        String thatClass = getThatDeclared().getThisSimpleName();
        this.simpleName = NamingStrategy.copierOf(naming, thisClass, thatClass);
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

    private JavaElemMethod doConvert(JavaCodeMethodBlockAddr scripts) {
        scripts.returning("{} == null ? null : unsafeCopy({}, new {}())",

            THIS, THIS, Imported.nameOf(getThatClassname()));
        return scripts.end();
    }

    private JavaElemMethod doUnsafeCopy(JavaCodeMethodBlockAddr scripts) {
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
        JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();
        if (isString(setterActualType)) {
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
        } else if (Test2.isSubtypeOf(getterActualType, setterActualType)) {
            scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
        } else if (Test2.isPrimitiveNumberSubtypeOf(getterActualType, setterActualType)) {
            scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
        } else if (Test2.isPrimitiveNumberSubtypeOf(setterActualType, getterActualType)) {
            scripts.scriptOf("{}.{}(({}) {}.{}())", THAT, setter.getMethodName(),

                setterActualType, THIS, getter.getMethodName());
        } else if (Test2.isPrimitiveBoolClass(setterActualType)) {
            if (Test2.isTypeof(getterActualType, Boolean.class)) {
                String var = defineGetterValueVar(scripts, getter);
                scripts.scriptOf("{}.{}({} != null && {})", THAT, setter.getMethodName(), var, var);
            } else {
                doMappingString2Boolean(scripts, setter, getter);
            }
        } else if (Test2.isTypeof(setterActualType, Boolean.class)) {
            if (Test2.isTypeof(getterActualType, boolean.class)) {
                scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
            } else {
                doMappingString2Boolean(scripts, setter, getter);
            }
        } else if (Test2.isPrimitiveCharClass(setterActualType)) {
            if (Test2.isTypeof(getterActualType, Character.class)) {
                String var = defineGetterValueVar(scripts, getter);
                scripts.onIfNotNull(var).scriptOf("{}.{}({})", THAT, setter.getMethodName(), var);
            }
        } else if (Test2.isTypeof(setterActualType, Character.class)) {
            if (Test2.isPrimitiveCharClass(getterActualType)) {
                scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
            }
        }
        doMappingString2Date(scripts, setter, getter);
    }

    private boolean doMappingString2Date(
        JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();
        if (isString(getterActualType)) {
            if (Test2.isTypeofAny(setterActualType, LocalDateTime.class, ZonedDateTime.class,

                OffsetDateTime.class, LocalDate.class, LocalTime.class, Year.class,

                OffsetTime.class, MonthDay.class, YearMonth.class)) {
                String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
                String var = defineGetterValueVar(scripts, getter);
                if (String2.isBlank(pattern)) {
                    scripts.scriptOf("{}.{}({} == null ? null : {}.parse({}))", THAT,

                        setter.getMethodName(), var, Imported.nameOf(setterActualType), var);
                } else {
                    String formatConst = defineJdk8DateTimeFormatter(scripts, pattern);
                    scripts.scriptOf("{}.{}({} == null ? null : {}.parse({}, {}))", THAT,

                        setter.getMethodName(), var, Imported.nameOf(setterActualType), var, formatConst);
                }
            } else if (Test2.isTypeof(setterActualType, Instant.class)) {
                String var = defineGetterValueVar(scripts, getter);
                scripts.scriptOf("{}.{}({} == null ? null : {}.parse({}))", THAT,

                    setter.getMethodName(), var, Imported.of(Instant.class), var);
            } else if (Test2.isTypeof(setterActualType, Date.class)) {
                String var = defineGetterValueVar(scripts, getter);
                String pattern = getFormatOrDefaultIfBlank(setter, getter, Formatter.DATETIME);
                scripts.scriptOf("{}.{}({} == null ? null : {}.parse({}, {}))", THAT,

                    setter.getMethodName(), var, Imported.FORMATTER, var, Stringify.of(pattern));
            } else if (Test2.isTypeof(setterActualType, Calendar.class)) {
                String var = defineGetterValueVar(scripts, getter);
                String pattern = getFormatOrDefaultIfBlank(setter, getter, Formatter.DATETIME);
                scripts.scriptOf("{}.{}({} == null ? null : {}.parseCalendar({}, {}))", THAT,

                    setter.getMethodName(), var, Imported.FORMATTER, var, Stringify.of(pattern));
            } else if (Test2.isTypeof(setterActualType, java.sql.Date.class)) {
                doMappingString2JavaSQLDate(scripts, setter, getter, Formatter.DATE);
            } else if (Test2.isTypeof(setterActualType, java.sql.Time.class)) {
                doMappingString2JavaSQLDate(scripts, setter, getter, Formatter.TIME);
            } else if (Test2.isTypeof(setterActualType, java.sql.Timestamp.class)) {
                doMappingString2JavaSQLDate(scripts, setter, getter, Formatter.DATETIME);
            } else if (Test2.isImportedAndJodaDateClass(setterActualType)) {
                if (Import2.JODA_TIME_2X &&
                    Test2.isTypeofAny(setterActualType,
                        DateTime.class,
                        org.joda.time.Instant.class,
                        org.joda.time.LocalDate.class,
                        org.joda.time.LocalTime.class,
                        org.joda.time.LocalDateTime.class,
                        org.joda.time.MutableDateTime.class,
                        org.joda.time.YearMonth.class,
                        org.joda.time.MonthDay.class)) {
                    doMappingString2Joda2xNormalDate(scripts, setter, getter, setterActualType);
                } else if (Import2.JODA_TIME_1X0 &&
                    Test2.isTypeofAny(setterActualType, DateTime.class, org.joda.time.MutableDateTime.class)) {
                    doMappingString2Joda1xNormalDate(scripts, setter, getter, setterActualType);
                } else if (Import2.JODA_TIME_1X3 &&
                    Test2.isTypeofAny(setterActualType,
                        org.joda.time.LocalDate.class,
                        org.joda.time.LocalTime.class,
                        org.joda.time.LocalDateTime.class)) {
                    doMappingString2Joda1xNormalDate(scripts, setter, getter, setterActualType);
                } else if (Import2.JODA_TIME_1X4 && Test2.isTypeofAny(setterActualType, Years.class,

                    Months.class, Weeks.class, Days.class, Hours.class, Minutes.class, Seconds.class)) {
                    String var = defineGetterValueVar(scripts, getter);
                    scripts.scriptOf("{}.{}({} == null ? null : {}.parse{}({}))", THAT,

                        setter.getMethodName(), var, Imported.nameOf(setterActualType),

                        Element2.getSimpleName(setterActualType), var);
                }
            } else {
                return false;
            }
            return true;
        }
        return false;
    }

    private void doMappingString2Joda1xNormalDate(
        JavaCodeMethodBlockAddr scripts,
        PropertyMethodDeclared setter,
        PropertyMethodDeclared getter,
        String setterActualType
    ) {
        String var = defineGetterValueVar(scripts, getter);
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        String setterSimpleType = Element2.getSimpleName(setterActualType);
        if (String2.isBlank(pattern)) {
            VarSupplier<JavaElemField> fieldHelper = scripts.fieldsHelper();
            String formatterName = org.joda.time.format.DateTimeFormatter.class.getCanonicalName();
            String formatterKey = String2.keyOf(formatterName, setterActualType);
            String formatterConst = fieldHelper.nextConstVar(formatterKey);
            if (!fieldHelper.contains(formatterConst)) {
                String method = "dateTime";
                if (Import2.JODA_TIME_1X3) {
                    if (Test2.isTypeof(setterActualType, org.joda.time.LocalDate.class)) {
                        method = "date";
                    } else if (Test2.isTypeof(setterActualType, org.joda.time.LocalTime.class)) {
                        method = "time";
                    }
                }
                fieldHelper.declareField(formatterConst, formatterName)
                    .assign()
                    .valueOfFormatted("{}.{}()", Imported.of(ISODateTimeFormat.class), method);
            }
            scripts.scriptOf("{}.{}({} == null ? null : {}.parse{}({}))", THAT,

                setter.getMethodName(), var, formatterConst, setterSimpleType, var);
        } else {
            String formatConst = defineJodaDateTimeFormatter(scripts, pattern);
            scripts.scriptOf("{}.{}({} == null ? null : {}.parse{}({}))", THAT,

                setter.getMethodName(), var, formatConst, setterSimpleType, var);
        }
    }

    private void doMappingString2Joda2xNormalDate(
        JavaCodeMethodBlockAddr scripts,
        PropertyMethodDeclared setter,
        PropertyMethodDeclared getter,
        String setterActualType
    ) {
        String var = defineGetterValueVar(scripts, getter);
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        Imported<String> setterImported = Imported.nameOf(setterActualType);
        if (String2.isBlank(pattern)) {
            scripts.scriptOf("{}.{}({} == null ? null : {}.parse({}))", THAT,

                setter.getMethodName(), var, setterImported, var);
        } else {
            String formatConst = defineJodaDateTimeFormatter(scripts, pattern);
            scripts.scriptOf("{}.{}({} == null ? null : {}.parse({}, {}))", THAT,

                setter.getMethodName(), var, setterImported, var, formatConst);
        }
    }

    private void doMappingString2JavaSQLDate(
        JavaCodeMethodBlockAddr scripts,
        PropertyMethodDeclared setter,
        PropertyMethodDeclared getter,
        String defaultPattern
    ) {
        String pattern = getFormatOrDefaultIfBlank(setter, getter, defaultPattern);
        String var = defineGetterValueVar(scripts, getter);
        scripts.scriptOf("{}.{}({} == null ? null : new {}({}.parse({}, {}).getTime()))", THAT,

            setter.getMethodName(), var, Imported.nameOf(setter.getPropertyActualType()),

            Imported.FORMATTER, var, Stringify.of(pattern));
    }

    private void doMapping2BigInteger(
        JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        final String longPrimitiveClass = "long";
        String getterActualType = getter.getPropertyActualType();
        if (Test2.isPrimitiveNumberClass(getterActualType)) {
            if (Test2.isPrimitiveNumberSubtypeOf(getterActualType, longPrimitiveClass) ||
                longPrimitiveClass.equals(getterActualType)) {
                scripts.scriptOf("{}.{}({}.valueOf({}.{}()))", THAT, setter.getMethodName(),

                    Imported.BIG_INTEGER, THIS, getter.getMethodName());
            } else {
                scripts.scriptOf("{}.{}({}.valueOf((long) {}.{}()))", THAT, setter.getMethodName(),

                    Imported.BIG_INTEGER, THIS, getter.getMethodName());
            }
        } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}.longValue()))", THAT,

                setter.getMethodName(), var, Imported.BIG_INTEGER, var);
        } else if (isString(getterActualType)) {
            final String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
            String var = defineGetterValueVar(scripts, getter);
            if (String2.isBlank(pattern)) {
                scripts.onStringIfNotEmpty(var).scriptOf("{}.{}(new {}({}))",

                    THAT, setter.getMethodName(), Imported.BIG_INTEGER, var);
            } else {
                String template = "{}.{}({}.valueOf({}.parseNumber({}, {}).longValue()))";
                scripts.onStringIfNotEmpty(var)
                    .scriptOf(template,
                        THAT,
                        setter.getMethodName(),
                        Imported.BIG_INTEGER,
                        Imported.FORMATTER,
                        var,
                        Stringify.of(pattern));
            }
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
        JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String getterActualType = getter.getPropertyActualType();
        if (Test2.isPrimitiveNumberClass(getterActualType)) {
            scripts.scriptOf("{}.{}({}.valueOf({}.{}()))", THAT, setter.getMethodName(),

                Imported.BIG_DECIMAL, THIS, getter.getMethodName());
        } else if (Test2.isWrappedNumberClass(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}))", THAT,

                setter.getMethodName(), var, Imported.BIG_DECIMAL, var);
        } else if (isString(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
            if (String2.isBlank(pattern)) {
                scripts.onStringIfNotEmpty(var).scriptOf("{}.{}(new {}({}))",

                    THAT, setter.getMethodName(), Imported.BIG_DECIMAL, var);
            } else {
                scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parseBigDecimal({}, {}))", THAT,

                    setter.getMethodName(), Imported.FORMATTER, var, Stringify.of(pattern));
            }
            scripts.onElse().scriptOf("{}.{}(null)", THAT, setter.getMethodName());
        } else if (BIG_INTEGER_CLASS.equals(getterActualType)

            || Objects.equals(getterActualType, "char[]")) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : new {}({}))", THAT,

                setter.getMethodName(), var, Imported.BIG_DECIMAL, var);
        } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}.doubleValue()))", THAT,

                setter.getMethodName(), var, Imported.BIG_DECIMAL, var);
        }
    }

    private void doMapping2PrimitiveNumber(
        JavaCodeMethodBlockAddr scripts,
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
        } else if (isString(getterActualType)) {
            final String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
            final String capitalizedSetterPrimitiveType = String2.capitalize(setterPrimitiveType);
            String setterWrappedClass = JAVA_DOT_LANG_DOT +
                (capitalizedSetterPrimitiveType.equals(INT_CAPITALIZED) ? "Integer" : capitalizedSetterPrimitiveType);
            String var = defineGetterValueVar(scripts, getter);
            if (String2.isBlank(pattern)) {
                scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parse{}({}))", THAT,

                    setter.getMethodName(), Imported.nameOf(setterWrappedClass),

                    capitalizedSetterPrimitiveType, var);
            } else {
                scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parseNumber({}, {}).{}Value())",

                    THAT, setter.getMethodName(), Imported.FORMATTER,

                    var, Stringify.of(pattern), setterPrimitiveType);
            }
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
        JavaCodeMethodBlockAddr scripts,
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

                Imported.nameOf(setterActualType), THIS, getter.getMethodName());
        } else if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, getterActualType)) {
            scripts.scriptOf("{}.{}(({}) {}.{}())", THAT, setter.getMethodName(),

                setterPrimitiveType, THIS, getter.getMethodName());
        } else if (Test2.isWrappedNumberClass(getterActualType) || Test2.isSubtypeOf(getterActualType, Number.class)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.{}Value())", THAT,

                setter.getMethodName(), var, var, setterPrimitiveType);
        } else if (isString(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
            if (String2.isBlank(pattern)) {
                scripts.onStringIfNotEmpty(var)
                    .scriptOf("{}.{}({}.valueOf({}))",
                        THAT,
                        setter.getMethodName(),
                        Imported.nameOf(setterActualType),
                        var);
            } else {
                scripts.onStringIfNotEmpty(var)
                    .scriptOf("{}.{}({}.parseNumber({}, {}).{}Value())",
                        THAT,
                        setter.getMethodName(),
                        Imported.FORMATTER,
                        var,
                        Stringify.of(pattern),
                        setterPrimitiveType);
            }
            scripts.onElse().scriptOf("{}.{}(null)", THAT, setter.getMethodName());
        } else if (Test2.isEnumClass(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, INT_PRIMITIVE_CLASS)) {
                scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf(({}) {}.ordinal()))", THAT,

                    setter.getMethodName(), var, Imported.nameOf(setterActualType), setterPrimitiveType, var);
            } else {
                scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}.ordinal()))", THAT,

                    setter.getMethodName(), var, Imported.nameOf(setterActualType), var);
            }
        } else if (isMathNumber(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.{}Value())", THAT,

                setter.getMethodName(), var, var, setterPrimitiveType);
        }
    }

    private void doMappingString2Boolean(
        JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String getterActualType = getter.getPropertyActualType();
        if (Test2.isTypeof(getterActualType, String.class)) {
            scripts.scriptOf("{}.{}({}.parseBoolean({}.{}()))", THAT,

                setter.getMethodName(), Imported.of(Boolean.class), THIS, getter.getMethodName());
        } else if (Test2.isSubtypeOf(getterActualType, CharSequence.class)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} != null && {}.parseBoolean({}.toString()))",

                THAT, setter.getMethodName(), var, Imported.of(Boolean.class), var);
        }
    }

    private boolean doMappingOnConversion(
        JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();
        Conversion conversion = Conversions.findAssignedConversion(getterActualType, setterActualType);
        if (conversion != null) {
            Imported<String> convertClass = Imported.nameOf(conversion.getConvertClass());
            if (Test2.isPrimitiveClass(getterActualType)) {
                scripts.scriptOf("{}.{}({}.{}({}.{}()))", THAT, setter.getMethodName(),

                    convertClass, conversion.getConvertMethodName(), THIS, getter.getMethodName());
            } else if (Test2.isPrimitiveClass(setterActualType)) {
                String var = defineGetterValueVar(scripts, getter);
                scripts.onIfNotNull(var).scriptOf("{}.{}({}.{}({}))", THAT, setter.getMethodName(),

                    convertClass, conversion.getConvertMethodName(), var);
            } else {
                String var = defineGetterValueVar(scripts, getter);
                scripts.scriptOf("{}.{}({} == null ? null : {}.{}({}))", THAT, setter.getMethodName(),

                    var, convertClass, conversion.getConvertMethodName(), var);
            }
            return true;
        }
        return false;
    }

    private void doMappingToEnum(
        JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();

        if (Test2.isSubtypeOf(getterActualType, String.class)) {
            Converter.ENUM.doConvert(scripts, setter, getter);
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

                setter.getMethodName(), var, Imported.nameOf(setterActualType), var);
        }
    }

    private void doMapping2String(
        JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String getterActualType = getter.getPropertyActualType();
        if (isString(getterActualType)) {
            scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
        } else if (Test2.isSubtypeOf(getterActualType, Number.class)) {
            String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
            if (String2.isBlank(pattern)) {
                doMapping2SimpleString(scripts, setter, getter);
            } else {
                String var = defineGetterValueVar(scripts, getter);
                scripts.scriptOf("{}.{}({} == null ? null : {}.format({}, {}))",
                    THAT,
                    setter.getMethodName(),
                    var,
                    Imported.FORMATTER,
                    var,
                    Stringify.of(pattern));
            }
        } else if (Test2.isPrimitiveNumberClass(getterActualType)) {
            String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
            if (String2.isBlank(pattern)) {
                scripts.scriptOf("{}.{}({}.valueOf({}.{}()))",
                    THAT,
                    setter.getMethodName(),
                    Imported.STRING,
                    THIS,
                    getter.getMethodName());
            } else {
                scripts.scriptOf("{}.{}({}.format({}.{}(), {}))",
                    THAT,
                    setter.getMethodName(),
                    Imported.FORMATTER,
                    THIS,
                    getter.getMethodName(),
                    Stringify.of(pattern));
            }
        } else if (Test2.isPrimitiveBoolClass(getterActualType) || Test2.isPrimitiveCharClass(getterActualType)) {
            scripts.scriptOf("{}.{}({}.valueOf({}.{}()))",
                THAT,
                setter.getMethodName(),
                Imported.STRING,
                THIS,
                getter.getMethodName());
        } else if (Test2.isEnumClass(getterActualType)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.name())", THAT, setter.getMethodName(), var, var);
        } else if (Test2.isSubtypeOf(getterActualType, TemporalAccessor.class)) {
            String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
            if (String2.isBlank(pattern)) {
                String formatConst = defineJdk8DateTimeFormatter(scripts, pattern);
                String var = defineGetterValueVar(scripts, getter);
                scripts.scriptOf("{}.{}({} == null ? null : {}.format({}))",

                    THAT, setter.getMethodName(), var, formatConst, var);
            } else {
                doMapping2SimpleString(scripts, setter, getter);
            }
        } else if (Test2.isImportedAndJodaDateClass(getterActualType)) {
            String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
            if (String2.isBlank(pattern)) {
                String formatConst = defineJodaDateTimeFormatter(scripts, pattern);
                String var = defineGetterValueVar(scripts, getter);
                scripts.scriptOf("{}.{}({} == null ? null : {}.print({}))",

                    THAT, setter.getMethodName(), var, formatConst, var);
            } else {
                doMapping2SimpleString(scripts, setter, getter);
            }
        } else if (Test2.isTypeof(getterActualType, java.sql.Time.class)) {
            doMappingUtilDate2String(scripts, setter, getter, Formatter.TIME);
        } else if (Test2.isTypeof(getterActualType, java.sql.Date.class)) {
            doMappingUtilDate2String(scripts, setter, getter, Formatter.DATE);
        } else if (Test2.isSubtypeOf(getterActualType, Date.class) ||
            Test2.isSubtypeOf(getterActualType, Calendar.class)) {
            doMappingUtilDate2String(scripts, setter, getter, Formatter.DATETIME);
        } else {
            doMapping2SimpleString(scripts, setter, getter);
        }
    }

    private void doMappingUtilDate2String(
        JavaCodeMethodBlockAddr scripts,
        PropertyMethodDeclared setter,
        PropertyMethodDeclared getter,
        String defaultPattern
    ) {
        Stringify stringify = Stringify.of(getFormatOrDefaultIfBlank(setter, getter, defaultPattern));
        String var = defineGetterValueVar(scripts, getter);
        scripts.scriptOf("{}.{}({} == null ? null : {}.format({}, {}))", THAT,

            setter.getMethodName(), var, Imported.FORMATTER, var, stringify);
    }

    private void doMapping2SimpleString(
        JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        scripts.scriptOf("{}.{}({} == null ? null : {}.toString())", THAT, setter.getMethodName(), var, var);
    }

    private String defineGetterValueVar(JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared getter) {
        String var = scripts.varsHelper().next();
        String getterActualType = getter.getPropertyActualType();
        scripts.scriptOf("{} {} = {}.{}()", Imported.nameOf(getterActualType), var, THIS, getter.getMethodName());
        return var;
    }

    private String defineEnumValues(JavaCodeMethodBlockAddr scripts, String enumClassname) {
        VarSupplier<JavaElemField> fieldsSupplier = scripts.fieldsHelper();
        String constVar = fieldsSupplier.nextConstVar(enumClassname);
        if (fieldsSupplier.contains(constVar)) {
            return constVar;
        }
        fieldsSupplier.declareField(constVar, "{}[]", enumClassname)
            .assign()
            .valueOfFormatted("{}.values()", Imported.nameOf(enumClassname))
            .end()
            .modifierWithAll(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        return constVar;
    }

    private String defineJodaDateTimeFormatter(JavaCodeMethodBlockAddr scripts, String pattern) {
        VarSupplier<JavaElemField> fieldsSupplier = scripts.fieldsHelper();
        String formatterName = DateTimeFormat.class.getCanonicalName();
        String patternKey = String2.keyOf(formatterName, pattern);
        String constVar = fieldsSupplier.nextConstVar(patternKey);
        if (fieldsSupplier.contains(constVar)) {
            return constVar;
        }
        fieldsSupplier.declareField(constVar, org.joda.time.format.DateTimeFormatter.class.getCanonicalName())
            .assign()
            .valueOfFormatted("{}.forPattern({})", Imported.nameOf(formatterName), Stringify.of(pattern))
            .end()
            .modifierWithAll(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        return constVar;
    }

    private String defineJdk8DateTimeFormatter(JavaCodeMethodBlockAddr scripts, String pattern) {
        VarSupplier<JavaElemField> fieldsSupplier = scripts.fieldsHelper();
        String formatterName = DateTimeFormatter.class.getCanonicalName();
        String patternKey = String2.keyOf(formatterName, pattern);
        String constVar = fieldsSupplier.nextConstVar(patternKey);
        if (fieldsSupplier.contains(constVar)) {
            return constVar;
        }
        fieldsSupplier.declareField(constVar, formatterName)
            .assign()
            .valueOfFormatted("{}.ofPattern({})", Imported.nameOf(formatterName), Stringify.of(pattern))
            .end()
            .modifierWithAll(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        return constVar;
    }

    private String getFormatOrDefaultIfBlank(
        PropertyMethodDeclared setter, PropertyMethodDeclared getter, String defaultPattern
    ) {
        Mapping mapping = getFormatDefinition(setter, getter);
        return mapping == null ? defaultPattern : String2.isBlank(mapping.format()) ? defaultPattern : mapping.format();
    }

    private Mapping getFormatDefinition(PropertyMethodDeclared setter, PropertyMethodDeclared getter) {
        Mapping[] formats = getter.getMethodAnnotations(Mapping.class);
        if (formats == null || formats.length == 0) {
            formats = getter.getFieldAnnotations(Mapping.class);
        }
        if (formats == null || formats.length == 0) {
            formats = setter.getMethodAnnotations(Mapping.class);
        }
        if (formats == null || formats.length == 0) {
            formats = setter.getFieldAnnotations(Mapping.class);
        }
        return formats == null || formats.length == 0 ? null : formats[0];
    }
}
