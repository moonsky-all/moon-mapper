package com.moonsky.mapper.util;

/**
 * @author benshaoye
 */
@FunctionalInterface
public interface ToShortFunction<T> {

    byte applyAsShort(T value);
}
