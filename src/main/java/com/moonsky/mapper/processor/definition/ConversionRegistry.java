package com.moonsky.mapper.processor.definition;

import com.moonsky.mapper.util.UseForConverter;
import com.moonsky.processor.processing.util.*;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * @author benshaoye
 */
enum ConversionRegistry {
    /** 单例 */
    INSTANCE,
    ;
    private final Map<ConversionKey, Conversion> conversions = new ConcurrentHashMap<>();
    private final Map<String, List<MatchesConversion>> matchesConversions = new HashMap<>();
    private final Map<String, List<MatchesConversion>> downgradesConversions = new HashMap<>();

    void register(String fromClass, String toClass, Conversion conversion) {
        conversions.put(new ConversionKey(fromClass, toClass), conversion);
    }

    void registerMatches(Predicate<String> fromActualTypeTester, String toClass, Conversion conversion) {
        register(matchesConversions, toClass, fromActualTypeTester, conversion);
    }

    void registerDowngrades(String toClass, Conversion conversion) {
        register(downgradesConversions, toClass, v -> true, conversion);
    }

    private static void register(
        Map<String, List<MatchesConversion>> conversions,
        String toClass,
        Predicate<String> fromActualTypeTester,
        Conversion conversion
    ) {
        List<MatchesConversion> all = conversions.computeIfAbsent(toClass, k -> new ArrayList<>());
        all.add(new MatchesConversion(fromActualTypeTester, conversion));
    }

    static {
        try {
            String convertClass;
            if (Import2.JODA_TIME_2X) {
                convertClass = AliasConstant2.Joda2xConvert_ClassName;
            } else if (Import2.JODA_TIME_1X0) {
                convertClass = AliasConstant2.Joda1xConvert_ClassName;
            } else {
                convertClass = AliasConstant2.DateConvert_ClassName;
            }
            extractConversions(Element2.getTypeElement(convertClass));
        } catch (Throwable ignored) {}

        /* assigned --------------------------------------------------------------------- */

        new FromPrimitiveCharOrPrimitiveBoolean2StringConversion().register(INSTANCE);
        new FromPrimitiveCharArray2BigDecimalConversion().register(INSTANCE);
        new FromPrimitiveNumber2JodaTime1x4Conversion().register(INSTANCE);
        new FromPrimitiveNumber2StringConversion().register(INSTANCE);

        new FromPrimitiveNumber2WrappedNumberDownwardIncompatibleConversion().register(INSTANCE);
        new FromPrimitiveNumber2WrappedNumberUpwardIncompatibleConversion().register(INSTANCE);
        new FromPrimitiveNumber2BigIntegerConversion().register(INSTANCE);
        new FromPrimitiveNumber2BigDecimalConversion().register(INSTANCE);
        new FromWrappedNumber2PrimitiveNumberIncompatibleConversion().register(INSTANCE);
        new FromWrappedNumber2PrimitiveNumberCompatibleConversion().register(INSTANCE);
        new FromWrappedNumber2BigDecimalConversion().register(INSTANCE);
        new FromWrappedNumber2BigIntegerConversion().register(INSTANCE);
        new PrimitiveNumberIncompatibleConversion().register(INSTANCE);
        new PrimitiveNumberCompatibleConversion().register(INSTANCE);
        new WrappedNumberIncompatibleConversion().register(INSTANCE);
        new WrappedNumberCompatibleConversion().register(INSTANCE);
        new MatchedPrimitive2WrappedConversion().register(INSTANCE);
        new MatchedWrapped2PrimitiveConversion().register(INSTANCE);

        new FromBitInteger2BigDecimalConversion().register(INSTANCE);
        new FromBigDecimal2BitIntegerConversion().register(INSTANCE);
        new FromEveryNumber2NumberConversion().register(INSTANCE);

        new FromWrappedBoolean2PrimitiveBooleanConversion().register(INSTANCE);

        new FromString2BooleanConversion().register(INSTANCE);
        new FromString2BigDecimalConversion().register(INSTANCE);
        new FromString2BigIntegerConversion().register(INSTANCE);
        new FromString2PrimitiveNumberConversion().register(INSTANCE);
        new FromString2WrappedNumberConversion().register(INSTANCE);
        new FromString2NumberConversion().register(INSTANCE);

        new FromString2UtilCalendarConversion().register(INSTANCE);
        new FromString2UtilDateConversion().register(INSTANCE);
        new FromString2Jdk8DateTimeConversion().register(INSTANCE);
        new FromString2Jdk8InstantConversion().register(INSTANCE);
        new FromString2JdkSqlDateConversion().register(INSTANCE);
        new FromString2JdkSqlTimeConversion().register(INSTANCE);
        new FromString2JdkSqlTimestampConversion().register(INSTANCE);
        new FromString2JodaTime1x0Conversion().register(INSTANCE);
        new FromString2JodaTime1x3Conversion().register(INSTANCE);
        new FromString2JodaTime1x4Conversion().register(INSTANCE);
        new FromString2JodaTime2x0Conversion().register(INSTANCE);
        new FromJoda1x4Time2PrimitiveNumberConversion().register(INSTANCE);
        new FromJoda1x4Time2WrappedNumberConversion().register(INSTANCE);
        new FromJoda1x4Time2StringConversion().register(INSTANCE);
        new FromJoda2x0TimeStringConversion().register(INSTANCE);
        new FromJdk8Time2PrimitiveNumberConversion().register(INSTANCE);
        new FromJdk8Time2WrappedNumberConversion().register(INSTANCE);

        new FromJdkSqlTime2StringConversion().register(INSTANCE);
        new FromJdkSqlDate2StringConversion().register(INSTANCE);
        // util.Date、util.Calendar、sql.Timestamp
        new FromJdkUtilDate2StringConversion().register(INSTANCE);

        /* matches ---------------------------------------------------------------------- */

        new FromCharSequence2PrimitiveBooleanMatchesConversion().register(INSTANCE);
        new FromCharSequence2Joda1x4TimeMatchesConversion().register(INSTANCE);

        new FromTemporalAccessor2StringMatchesConversion().register(INSTANCE);
        new FromNumber2Joda1x4TimeMatchesConversion().register(INSTANCE);

        new FromNumber2PrimitiveNumberMatchesConversion().register(INSTANCE);
        new FromNumber2WrappedNumberMatchesConversion().register(INSTANCE);
        new FromNumber2BigDecimalMatchesConversion().register(INSTANCE);
        new FromNumber2BigIntegerMatchesConversion().register(INSTANCE);
        new FromNumber2StringMatchesConversion().register(INSTANCE);

        new FromEnum2PrimitiveNumberMatchesConversion().register(INSTANCE);
        new FromEnum2WrappedNumberMatchesConversion().register(INSTANCE);
        new FromEnum2StringMatchesConversion().register(INSTANCE);

        /* downgrades ------------------------------------------------------------------- */

        new FromObject2StringDowngradesConversion().register(INSTANCE);
    }

    private static void extractConversions(TypeElement convertElem) {
        UseForConverter converterDef = convertElem.getAnnotation(UseForConverter.class);
        boolean classConvertible = converterDef == null || converterDef.value();
        loadThisClass(convertElem, classConvertible);
        if (converterDef == null || !converterDef.suppressSuper()) {
            loadSuperclass(convertElem);
        }
    }

    private static void loadThisClass(
        TypeElement convertElem, boolean classConvertible
    ) {
        final String convertClass = Element2.getQualifiedName(convertElem);
        List<? extends Element> elements = convertElem.getEnclosedElements();
        for (Element element : elements) {
            // 构造函数不可用作转换器
            if (!Test2.isMethod(element)) {
                continue;
            }
            // 只有静态公共方法可用作转换器
            if (!Test2.isAll(element, Modifier.STATIC, Modifier.PUBLIC)) {
                continue;
            }
            // 指定了非转换器不会用作转换器
            UseForConverter converterDef = element.getAnnotation(UseForConverter.class);
            if (converterDef == null && !classConvertible) {
                continue;
            } else if (converterDef != null && !converterDef.value()) {
                continue;
            }
            ExecutableElement method = (ExecutableElement) element;
            TypeMirror toMirror = method.getReturnType();
            // 转换器必须有非 void 返回值类型
            if (Test2.isTypeof(toMirror.toString(), void.class)) {
                continue;
            }
            List<? extends VariableElement> parameters = method.getParameters();
            // 转换器只能接受一个入参
            if (parameters == null || parameters.size() != 1) {
                continue;
            }
            TypeMirror fromMirror = parameters.get(0).asType();
            Conversion conversion = new MethodConversionDetail(convertClass, method, fromMirror, toMirror);
            conversion.register(ConversionRegistry.INSTANCE);
        }
    }

    private static void loadSuperclass(TypeElement convertElem) {
        TypeMirror superclass = convertElem.getSuperclass();
        Element superElem = Processing2.getTypes().asElement(superclass);
        if (superElem != null && !superclass.toString().equals(Object.class.getCanonicalName())) {
            extractConversions((TypeElement) superElem);
        }
    }

    /**
     * 返回完全匹配的转换器
     *
     * @param fromClassname
     * @param toClassname
     *
     * @return
     */
    public static Conversion find(String fromClassname, String toClassname) {
        Conversion conversion = INSTANCE.conversions.get(new ConversionKey(fromClassname, toClassname));
        if (conversion != null) {
            return conversion;
        }
        conversion = findMatches(INSTANCE.matchesConversions.get(toClassname), fromClassname);
        if (conversion != null) {
            return conversion;
        }
        conversion = findMatches(INSTANCE.downgradesConversions.get(toClassname), fromClassname);
        return conversion == null ? null : conversion;
    }

    private static Conversion findMatches(
        List<MatchesConversion> conversions, String fromClassname
    ) {
        if (conversions != null) {
            for (MatchesConversion conversion : conversions) {
                if (conversion.matches(fromClassname)) {
                    return conversion;
                }
            }
        }
        return null;
    }

    private final static class ConversionKey {

        private final String fromClassname;
        private final String toClassname;
        private final int hash;

        private ConversionKey(String fromClassname, String toClassname) {
            this.hash = (Objects.hashCode(fromClassname) + Objects.hashCode(toClassname) + 1) * 31;
            this.fromClassname = fromClassname;
            this.toClassname = toClassname;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof ConversionKey) {
                ConversionKey that = (ConversionKey) o;
                return Objects.equals(fromClassname, that.fromClassname) && Objects.equals(toClassname,
                    that.toClassname);
            }
            return false;
        }

        @Override
        public int hashCode() {return hash;}
    }
}
