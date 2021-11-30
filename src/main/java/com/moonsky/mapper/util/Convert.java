package com.moonsky.mapper.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.*;

/**
 * @author benshaoye
 */
public enum Convert {
    ;

    private static boolean isEmpty(CharSequence value) {
        return value == null || value.length() == 0;
    }

    public static final String DATE = "yyyy-MM-dd";
    public static final String TIME = "HH:mm:ss";
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date value, String pattern) {
        return new SimpleDateFormat(pattern).format(value);
    }

    public static String format(Calendar value, String pattern) {
        return new SimpleDateFormat(pattern).format(value.getTime());
    }

    public static String format(long value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

    public static String format(double value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

    public static String format(Number value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }

    public static Number parseNumber(String value, String pattern) {
        try {
            return new DecimalFormat(pattern).parse(value);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    public static BigDecimal parseBigDecimal(String value, String pattern) {
        DecimalFormat format = new DecimalFormat(pattern);
        format.setParseBigDecimal(true);
        try {
            return (BigDecimal) format.parse(value);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Calendar parseCalendar(String value, String pattern) {
        return DateConvert.toUtilCalendar(parseUtilDate(value, pattern));
    }

    public static Date parseUtilDate(String value, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(value);
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T extends CharSequence, O> O parseAny(T value, Function<T, O> converter) {
        return isEmpty(value) ? null : converter.apply(value);
    }

    public static <T extends CharSequence, O> O parseAny(T value, Function<T, O> converter, O defaultValue) {
        return isEmpty(value) ? defaultValue : converter.apply(value);
    }

    public static <T, O> O useAny(T value, Function<T, O> converter) {
        return value == null ? null : converter.apply(value);
    }

    public static <T, O> O useAny(T value, Function<T, O> converter, O defaultValue) {
        return value == null ? defaultValue : converter.apply(value);
    }

    public static <T> boolean toBoolean(T value, Predicate<T> tester) {
        return value != null && tester.test(value);
    }

    public static <T> boolean toBoolean(T value, Predicate<T> tester, boolean defaultValue) {
        return value == null ? defaultValue : tester.test(value);
    }

    public static <T extends CharSequence> boolean parseBoolean(T value, Predicate<T> tester) {
        return !isEmpty(value) && tester.test(value);
    }

    public static <T extends CharSequence> boolean parseBoolean(T value, Predicate<T> tester, boolean defaultValue) {
        return isEmpty(value) ? defaultValue : tester.test(value);
    }

    public static <T> byte toByte(T value, ToByteFunction<T> converter) {
        return value == null ? 0 : converter.applyAsByte(value);
    }

    public static <T> byte toByte(T value, ToByteFunction<T> converter, byte defaultValue) {
        return value == null ? defaultValue : converter.applyAsByte(value);
    }

    public static <T extends CharSequence> byte parseByte(T value, ToByteFunction<T> converter) {
        return isEmpty(value) ? 0 : converter.applyAsByte(value);
    }

    public static <T extends CharSequence> byte parseByte(T value, ToByteFunction<T> converter, byte defaultValue) {
        return isEmpty(value) ? defaultValue : converter.applyAsByte(value);
    }

    public static <T> short toShort(T value, ToShortFunction<T> converter) {
        return value == null ? 0 : converter.applyAsShort(value);
    }

    public static <T> short toShort(T value, ToShortFunction<T> converter, short defaultValue) {
        return value == null ? defaultValue : converter.applyAsShort(value);
    }

    public static <T extends CharSequence> short parseShort(T value, ToShortFunction<T> converter) {
        return isEmpty(value) ? 0 : converter.applyAsShort(value);
    }

    public static <T extends CharSequence> short parseShort(T value, ToShortFunction<T> converter, short defaultValue) {
        return isEmpty(value) ? defaultValue : converter.applyAsShort(value);
    }

    public static <T> int toInt(T value, ToIntFunction<T> converter) {
        return value == null ? 0 : converter.applyAsInt(value);
    }

    public static <T> int toInt(T value, ToIntFunction<T> converter, int defaultValue) {
        return value == null ? defaultValue : converter.applyAsInt(value);
    }

    public static <T extends CharSequence> int parseInt(T value, ToIntFunction<T> converter) {
        return isEmpty(value) ? 0 : converter.applyAsInt(value);
    }

    public static <T extends CharSequence> int parseInt(T value, ToIntFunction<T> converter, int defaultValue) {
        return isEmpty(value) ? defaultValue : converter.applyAsInt(value);
    }

    public static <T> long toLong(T value, ToLongFunction<T> converter) {
        return value == null ? 0 : converter.applyAsLong(value);
    }

    public static <T> long toLong(T value, ToLongFunction<T> converter, long defaultValue) {
        return value == null ? defaultValue : converter.applyAsLong(value);
    }

    public static <T extends CharSequence> long parseLong(T value, ToLongFunction<T> converter) {
        return isEmpty(value) ? 0 : converter.applyAsLong(value);
    }

    public static <T extends CharSequence> long parseLong(T value, ToLongFunction<T> converter, long defaultValue) {
        return isEmpty(value) ? defaultValue : converter.applyAsLong(value);
    }

    public static <T> float toFloat(T value, ToFloatFunction<T> converter) {
        return value == null ? 0 : converter.applyAsFloat(value);
    }

    public static <T> float toFloat(T value, ToFloatFunction<T> converter, float defaultValue) {
        return value == null ? defaultValue : converter.applyAsFloat(value);
    }

    public static <T extends CharSequence> float parseFloat(T value, ToFloatFunction<T> converter) {
        return isEmpty(value) ? 0 : converter.applyAsFloat(value);
    }

    public static <T extends CharSequence> float parseFloat(T value, ToFloatFunction<T> converter, float defaultValue) {
        return isEmpty(value) ? defaultValue : converter.applyAsFloat(value);
    }

    public static <T> double toDouble(T value, ToDoubleFunction<T> converter) {
        return value == null ? 0 : converter.applyAsDouble(value);
    }

    public static <T> double toDouble(T value, ToDoubleFunction<T> converter, double defaultValue) {
        return value == null ? defaultValue : converter.applyAsDouble(value);
    }

    public static <T extends CharSequence> double parseDouble(T value, ToDoubleFunction<T> converter) {
        return isEmpty(value) ? 0 : converter.applyAsDouble(value);
    }

    public static <T extends CharSequence> double parseDouble(T value, ToDoubleFunction<T> converter, double defaultValue) {
        return isEmpty(value) ? defaultValue : converter.applyAsDouble(value);
    }
}
