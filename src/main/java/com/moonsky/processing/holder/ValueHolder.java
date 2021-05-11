package com.moonsky.processing.holder;

/**
 * @author benshaoye
 */
public class ValueHolder<T> {

    private T value;

    public ValueHolder() { }

    public void set(T value) { this.value = value; }

    public T get() { return value; }
}
