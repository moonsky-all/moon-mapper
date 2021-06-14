package com.moonsky.processing.util;

import com.moonsky.mapper.util.Formatter;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * @author benshaoye
 */
public class Imported<T> {

    public static final Imported<Class<?>> STRING = Imported.of(String.class);
    public static final Imported<Class<?>> BIG_INTEGER = Imported.of(BigInteger.class);
    public static final Imported<Class<?>> BIG_DECIMAL = Imported.of(BigDecimal.class);
    public static final Imported<Class<?>> FORMATTER = Imported.of(Formatter.class);

    private final T value;
    private final boolean classname;

    private Imported(T value, boolean classnameOnString) {
        this.classname = classnameOnString;
        this.value = value;
    }

    /**
     * 从这里进入的字符串将被认为是需要被 import 的完整类名
     *
     * @param classname 完整类名
     * @param <T>
     *
     * @return Import
     */
    public static <T extends CharSequence> Imported<T> nameOf(T classname) {
        return new Imported<>(classname, true);
    }

    /**
     * 从这里进入的 Class、TypeElement、TypeMirror 会通过 import 引入，并使用引用后的名称
     *
     * @param value
     * @param <T>
     *
     * @return
     */
    public static <T> Imported<T> of(T value) { return new Imported<>(value, false); }

    public T get() { return value; }

    private boolean isClassname() { return classname; }

    public String toString(Importer importer) {
        Object value = this.value;
        if (value instanceof Class<?>) {
            return importer.onImported((Class<?>) value);
        }
        if (value instanceof TypeElement) {
            return importer.onImported((TypeElement) value);
        }
        if (value instanceof TypeMirror) {
            return importer.onImported((TypeMirror) value);
        }
        if (value instanceof CharSequence && isClassname()) {
            return importer.onImported(value.toString());
        }
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Imported<?> anImport = (Imported<?>) o;
        return classname == anImport.classname && Objects.equals(value, anImport.value);
    }

    @Override
    public int hashCode() { return Objects.hash(value, classname); }

    @Override
    public String toString() { return String.valueOf(get()); }
}
