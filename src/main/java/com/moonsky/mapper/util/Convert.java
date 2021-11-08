package com.moonsky.mapper.util;

import java.util.function.*;

/**
 * @author benshaoye
 */
public abstract class Convert {

    protected Convert() {
        throw new AssertionError("No " + getClass().getCanonicalName() + " instances for you!");
    }

    public static <T extends CharSequence, O> O useString(T value, Function<T, O> converter) {
        return value == null || value.length() == 0 ? null : converter.apply(value);
    }

    public static <T extends CharSequence, O> O useString(T value, Function<T, O> converter, O defaultValue) {
        return value == null || value.length() == 0 ? defaultValue : converter.apply(value);
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

    public static <T> byte toByte(T value, ToByteFunction<T> converter) {
        return value == null ? 0 : converter.applyAsByte(value);
    }

    public static <T> byte toByte(T value, ToByteFunction<T> converter, byte defaultValue) {
        return value == null ? defaultValue : converter.applyAsByte(value);
    }

    public static <T> short toShort(T value, ToShortFunction<T> converter) {
        return value == null ? 0 : converter.applyAsShort(value);
    }

    public static <T> short toShort(T value, ToShortFunction<T> converter, short defaultValue) {
        return value == null ? defaultValue : converter.applyAsShort(value);
    }

    public static <T> int toInt(T value, ToIntFunction<T> converter) {
        return value == null ? 0 : converter.applyAsInt(value);
    }

    public static <T> int toInt(T value, ToIntFunction<T> converter, int defaultValue) {
        return value == null ? defaultValue : converter.applyAsInt(value);
    }

    public static <T> long toLong(T value, ToLongFunction<T> converter) {
        return value == null ? 0 : converter.applyAsLong(value);
    }

    public static <T> long toLong(T value, ToLongFunction<T> converter, long defaultValue) {
        return value == null ? defaultValue : converter.applyAsLong(value);
    }

    public static <T> float toFloat(T value, ToFloatFunction<T> converter) {
        return value == null ? 0 : converter.applyAsFloat(value);
    }

    public static <T> float toFloat(T value, ToFloatFunction<T> converter, float defaultValue) {
        return value == null ? defaultValue : converter.applyAsFloat(value);
    }

    public static <T> double toDouble(T value, ToDoubleFunction<T> converter) {
        return value == null ? 0 : converter.applyAsDouble(value);
    }

    public static <T> double toDouble(T value, ToDoubleFunction<T> converter, double defaultValue) {
        return value == null ? defaultValue : converter.applyAsDouble(value);
    }
}
