package com.moonsky.mapper.util;

/**
 * @author benshaoye
 */
@FunctionalInterface
public interface ToByteFunction<T> {

    byte applyAsByte(T value);
}
