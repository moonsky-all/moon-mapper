package com.moonsky.processing.holder;

import java.util.function.Supplier;

/**
 * @author benshaoye
 */
public class LazyHolder<T> {

    private final Supplier<T> supplier;
    private volatile T value;

    public LazyHolder(Supplier<T> supplier) { this.supplier = supplier; }

    public static <T> LazyHolder<T> of(Supplier<T> supplier) {
        return new LazyHolder<>(supplier);
    }

    public T get() {
        T val = value;
        if (val == null) {
            val = this.supplier.get();
            synchronized (this) {
                if (this.value == null) {
                    this.value = val;
                }
            }
        }
        return val;
    }
}
