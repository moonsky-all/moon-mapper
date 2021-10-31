package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.util.AliasConstant2;
import com.moonsky.processor.processing.util.Collect2;
import com.moonsky.processor.processing.util.Test2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Month;
import java.time.temporal.TemporalAccessor;
import java.util.*;

import static java.util.Collections.unmodifiableSet;

/**
 * @author benshaoye
 */
abstract class Classname {

    protected static final String PRIMITIVE_double = "double";
    protected static final String PRIMITIVE_float = "float";
    protected static final String PRIMITIVE_long = "long";
    protected static final String PRIMITIVE_int = "int";
    protected static final String PRIMITIVE_short = "short";
    protected static final String PRIMITIVE_byte = "byte";
    protected static final String PRIMITIVE_char = "char";
    protected static final String PRIMITIVE_boolean = "boolean";

    protected static final String CLASS_Character = classname(Character.class);
    protected static final String CLASS_Boolean = classname(Boolean.class);
    protected static final String CLASS_Double = classname(Double.class);
    protected static final String CLASS_Float = classname(Float.class);
    protected static final String CLASS_Long = classname(Long.class);
    protected static final String CLASS_Integer = classname(Integer.class);
    protected static final String CLASS_Short = classname(Short.class);
    protected static final String CLASS_Byte = classname(Byte.class);

    protected static final String CLASS_String = classname(String.class);
    protected static final String CLASS_Number = classname(Number.class);
    protected static final String CLASS_BigInteger = classname(BigInteger.class);
    protected static final String CLASS_BigDecimal = classname(BigDecimal.class);
    protected static final String CLASS_CharSequence = classname(CharSequence.class);
    protected static final String CLASS_TemporalAccessor = classname(TemporalAccessor.class);
    protected static final String CLASS_sql_Time = classname(java.sql.Time.class);
    protected static final String CLASS_sql_Date = classname(java.sql.Date.class);
    protected static final String CLASS_sql_Timestamp = classname(java.sql.Timestamp.class);
    protected static final String CLASS_util_Date = classname(java.util.Date.class);
    protected static final String CLASS_util_Calendar = classname(java.util.Calendar.class);

    protected static final String CLASS_jdk8_Month = classname(Month.class);

    @SuppressWarnings("all")
    protected static final Set<String> Joda_1x0_CLASSES;
    protected static final Set<String> Joda_1x3_CLASSES;
    protected static final Set<String> Joda_1x4_CLASSES;
    protected static final Set<String> Joda_2x0_CLASSES;
    protected static final List<String> PRIMITIVE_NUMBER_TYPES;
    protected static final List<String> WRAPPED_NUMBER_TYPES;

    private static final Map<String, String> MAP_WRAPPED_TO_PRIMITIVE;
    private static final Map<String, String> MAP_PRIMITIVE_TO_WRAPPED;

    static {
        Joda_1x0_CLASSES = asSet(AliasConstant2.Joda_DateTime_ClassName,//
            AliasConstant2.Joda_MutableDateTime_ClassName);
        Joda_1x3_CLASSES = asSet(AliasConstant2.Joda_LocalDate_ClassName,
            AliasConstant2.Joda_LocalTime_ClassName,
            AliasConstant2.Joda_LocalDateTime_ClassName);
        Joda_1x4_CLASSES = asSet(AliasConstant2.Joda_Years_ClassName,
            AliasConstant2.Joda_Months_ClassName,
            AliasConstant2.Joda_Weeks_ClassName,
            AliasConstant2.Joda_Days_ClassName,
            AliasConstant2.Joda_Hours_ClassName,
            AliasConstant2.Joda_Minutes_ClassName,
            AliasConstant2.Joda_Seconds_ClassName);
        Joda_2x0_CLASSES = asSet(AliasConstant2.Joda_DateTime_ClassName,
            AliasConstant2.Joda_Instant_ClassName,
            AliasConstant2.Joda_LocalDate_ClassName,
            AliasConstant2.Joda_LocalTime_ClassName,
            AliasConstant2.Joda_LocalDateTime_ClassName,
            AliasConstant2.Joda_MutableDateTime_ClassName,
            AliasConstant2.Joda_YearMonth_ClassName,
            AliasConstant2.Joda_MonthDay_ClassName);

        PRIMITIVE_NUMBER_TYPES = Collect2.list(PRIMITIVE_double,
            PRIMITIVE_float,
            PRIMITIVE_long,
            PRIMITIVE_int,
            PRIMITIVE_short,
            PRIMITIVE_byte);
        WRAPPED_NUMBER_TYPES = Collect2.list(CLASS_Double,
            CLASS_Float,
            CLASS_Long,
            CLASS_Integer,
            CLASS_Short,
            CLASS_Byte);

        Map<String, String> mapPrimitiveToWrapped = new HashMap<>();
        Map<String, String> mapWrappedToPrimitive = new HashMap<>();
        mapPrimitiveToWrapped.put(PRIMITIVE_boolean, CLASS_Boolean);
        mapPrimitiveToWrapped.put(PRIMITIVE_char, CLASS_Character);
        mapPrimitiveToWrapped.put(PRIMITIVE_double, CLASS_Double);
        mapPrimitiveToWrapped.put(PRIMITIVE_float, CLASS_Float);
        mapPrimitiveToWrapped.put(PRIMITIVE_long, CLASS_Long);
        mapPrimitiveToWrapped.put(PRIMITIVE_int, CLASS_Integer);
        mapPrimitiveToWrapped.put(PRIMITIVE_short, CLASS_Short);
        mapPrimitiveToWrapped.put(PRIMITIVE_byte, CLASS_Byte);

        mapWrappedToPrimitive.put(CLASS_Boolean, PRIMITIVE_boolean);
        mapWrappedToPrimitive.put(CLASS_Character, PRIMITIVE_char);
        mapWrappedToPrimitive.put(CLASS_Double, PRIMITIVE_double);
        mapWrappedToPrimitive.put(CLASS_Float, PRIMITIVE_float);
        mapWrappedToPrimitive.put(CLASS_Long, PRIMITIVE_long);
        mapWrappedToPrimitive.put(CLASS_Integer, PRIMITIVE_int);
        mapWrappedToPrimitive.put(CLASS_Short, PRIMITIVE_short);
        mapWrappedToPrimitive.put(CLASS_Byte, PRIMITIVE_byte);

        MAP_WRAPPED_TO_PRIMITIVE = Collections.unmodifiableMap(mapWrappedToPrimitive);
        MAP_PRIMITIVE_TO_WRAPPED = Collections.unmodifiableMap(mapPrimitiveToWrapped);
    }

    protected static final String classname(Class<?> klass) {return klass.getCanonicalName();}

    protected static final String findPrimitiveClass(String wrappedClass) {
        String primitiveClass = MAP_WRAPPED_TO_PRIMITIVE.get(wrappedClass);
        if (primitiveClass == null && Test2.isPrimitiveClass(wrappedClass)) {
            return wrappedClass;
        }
        return primitiveClass;
    }

    protected static final String findWrappedClass(String primitiveClass) {
        String wrappedClass = MAP_PRIMITIVE_TO_WRAPPED.get(primitiveClass);
        if (wrappedClass == null && Test2.isWrappedNumberClass(primitiveClass)) {
            return primitiveClass;
        }
        return wrappedClass;
    }

    private static final <T> Set<T> asSet(T... values) {return unmodifiableSet(Collect2.set(values));}
}
