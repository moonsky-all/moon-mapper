package com.moonsky.mapper.processing.util;

/**
 * @author benshaoye
 */
public enum Char2 {
    ;

    public static boolean isUpper(int ch) { return ch > 64 && ch < 91; }

    public static boolean isLower(int ch) { return ch > 96 && ch < 123; }

    public static boolean isLetter(int ch) { return isUpper(ch) || isLower(ch); }
}
