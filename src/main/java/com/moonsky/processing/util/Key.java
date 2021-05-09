package com.moonsky.processing.util;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author benshaoye
 */
public class Key {

    private final String[] keyFragments;
    private final int hashCode;

    public Key(String... keyFragments) {
        this.keyFragments = keyFragments;
        this.hashCode = Objects.hash(keyFragments);
    }

    @Override
    public int hashCode() { return hashCode; }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Key key = (Key) o;
        return hashCode == key.hashCode && Arrays.equals(keyFragments, key.keyFragments);
    }
}
