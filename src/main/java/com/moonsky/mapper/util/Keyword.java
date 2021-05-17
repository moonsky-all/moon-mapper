package com.moonsky.mapper.util;

import com.moonsky.mapper.annotation.MapperFor;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
public enum Keyword {
    /** copier */
    COPIER,
    /** mapper */
    MAPPER;

    public static String mapperOf(MapperFor mapperFor, String thisClass, String thatClass) {
        return with(mapperFor, thisClass, thatClass, Keyword.MAPPER);
    }

    public static String copierOf(MapperFor mapperFor, String thisClass, String thatClass) {
        return with(mapperFor, thisClass, thatClass, Keyword.COPIER);
    }

    public static String with(MapperFor mapperFor, Class<?> thisClass, Class<?> thatClass, Keyword type) {
        return with(mapperFor, thisClass.getCanonicalName(), thatClass.getCanonicalName(), type);
    }

    public static String with(MapperFor mapperFor, String thisClassname, String thatClassname, Keyword type) {
        String thisClass = getSimpleName(thisClassname);
        String thatClass = getSimpleName(thatClassname);

        String thisPrefix = "", thatPrefix = "";
        for (String prefix : mapperFor.trimNamePrefix()) {
            if (thisClass.startsWith(prefix)) {
                thisClass = thisClass.substring(prefix.length());
                thisPrefix = prefix;
            }
        }
        for (String prefix : mapperFor.trimNamePrefix()) {
            if (thatClass.startsWith(prefix)) {
                thatClass = thatClass.substring(prefix.length());
                thatPrefix = prefix;
            }
        }

        String thisSuffix = "", thatSuffix = "";
        for (String suffix : mapperFor.trimNameSuffix()) {
            if (thisClass.endsWith(suffix)) {
                thisClass = thisClass.substring(0, thisClass.length() - suffix.length());
                thisSuffix = suffix;
            }
        }
        for (String suffix : mapperFor.trimNameSuffix()) {
            if (thatClass.endsWith(suffix)) {
                thatClass = thatClass.substring(0, thatClass.length() - suffix.length());
                thatSuffix = suffix;
            }
        }

        String typeStringify = capitalize(type.name().toLowerCase());
        String namePattern = deleteWhitespace(mapperFor.namePattern());
        namePattern = replaceAll(namePattern, "{thisPrefix}", thisPrefix);
        namePattern = replaceAll(namePattern, "{thatPrefix}", thatPrefix);
        namePattern = replaceAll(namePattern, "{thisSuffix}", thisSuffix);
        namePattern = replaceAll(namePattern, "{thatSuffix}", thatSuffix);
        namePattern = replaceAll(namePattern, "{thisClass}", thisClass);
        namePattern = replaceAll(namePattern, "{thatClass}", thatClass);
        namePattern = replaceAll(namePattern, "{type}", typeStringify);
        boolean digitStarted = Character.isDigit(namePattern.charAt(0));
        return (digitStarted ? "M" : "") + namePattern;
    }

    public static String replaceAll(String input, String search, String replacement) {
        if (isEmpty(input) || isEmpty(search) || replacement == null) {
            return input;
        }
        int index = input.indexOf(search);
        if (index == -1) {
            return input;
        }
        int capacity = input.length();
        if (replacement.length() > search.length()) {
            capacity += 16;
        }
        StringBuilder sb = new StringBuilder(capacity);
        int idx = 0;
        int searchLen = search.length();
        while (index >= 0) {
            sb.append(input, idx, index);
            sb.append(replacement);
            idx = index + searchLen;
            index = input.indexOf(search, idx);
        }
        sb.append(input, idx, input.length());
        return sb.toString();
    }

    public static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1))) {
            return name;
        }
        char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static String deleteWhitespace(String value) {
        StringBuilder builder = new StringBuilder();
        for (char ch : value.toCharArray()) {
            if (!Character.isWhitespace(ch)) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    public static String getSimpleName(String fullName) {
        int last = fullName.indexOf("<"), idx;
        if (last > 0) {
            idx = fullName.lastIndexOf('.', last);
        } else {
            idx = fullName.lastIndexOf('.');
            last = fullName.length();
        }
        return fullName.substring(Math.max(idx + 1, 0), last);
    }

    public static String getPackageName(Class<?> klass) {
        return klass.getPackage().getName();
    }

    public static String getPackageName(String classname) {
        int last = classname.indexOf("<"), idx;
        if (last > 0) {
            idx = classname.lastIndexOf('.', last);
        } else {
            idx = classname.lastIndexOf('.');
        }
        return classname.substring(0, idx);
    }
}
