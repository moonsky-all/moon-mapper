package com.moonsky.mapper.util;

/**
 * @author benshaoye
 */
@FunctionalInterface
public interface ToFloatFunction<T> {

    float applyAsFloat(T value);
}
