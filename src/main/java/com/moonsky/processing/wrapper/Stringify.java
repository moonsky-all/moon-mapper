package com.moonsky.processing.wrapper;

import java.util.Objects;

/**
 * @author benshaoye
 */
public class Stringify {

    private final String value;

    public Stringify(Object value) { this.value = "\"" + value + '"'; }

    public static Stringify of(Object value) { return new Stringify(value); }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Stringify stringify = (Stringify) o;
        return Objects.equals(value, stringify.value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }

    @Override
    public String toString() { return value; }
}
