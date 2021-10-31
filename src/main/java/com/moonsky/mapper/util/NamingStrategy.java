package com.moonsky.mapper.util;

import com.moonsky.mapper.annotation.MapperNaming;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.moonsky.mapper.util.DefaultNaming.defaultSuffixes;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
public enum NamingStrategy {
    /** copier */
    COPIER(CopierNotFoundException::new, CopierNotFoundException::new),
    /** mapper */
    MAPPER(MapperNotFoundException::new, MapperNotFoundException::new);

    private final Function<String, RuntimeException> stringBuilder;
    private final BiFunction<String, Throwable, RuntimeException> messageCauseBuilder;

    static final String TRIM_PREFIXES = "trimPrefixes";
    static final String TRIM_SUFFIXES = "trimSuffixes";
    static final String PATTERN_NAME = "pattern";
    static final String[] EMPTIES = {};

    NamingStrategy(
        Function<String, RuntimeException> stringBuilder,
        BiFunction<String, Throwable, RuntimeException> messageCauseBuilder
    ) {
        this.messageCauseBuilder = messageCauseBuilder;
        this.stringBuilder = stringBuilder;
    }

    public RuntimeException newException(String message) {
        return stringBuilder.apply(message);
    }

    public RuntimeException newException(String message, Throwable cause) {
        return messageCauseBuilder.apply(message, cause);
    }

    /**
     * @param naming
     * @param thisClass
     * @param thatClass
     *
     * @return
     *
     * @see MapperNaming
     */
    public static String mapperOf(MapperNaming naming, String thisClass, String thatClass) {
        return with(naming, thisClass, thatClass, NamingStrategy.MAPPER);
    }

    /**
     * @param naming
     * @param thisClass
     * @param thatClass
     *
     * @return
     *
     * @see MapperNaming
     */
    public static String copierOf(MapperNaming naming, String thisClass, String thatClass) {
        return with(naming, thisClass, thatClass, NamingStrategy.COPIER);
    }

    /**
     * @param naming
     * @param thisClass
     * @param thatClass
     * @param type
     *
     * @return
     *
     * @see MapperNaming
     */
    public static String with(MapperNaming naming, Class<?> thisClass, Class<?> thatClass, NamingStrategy type) {
        return with(naming, thisClass.getCanonicalName(), thatClass.getCanonicalName(), type);
    }

    /**
     * @param naming
     * @param thisClassname
     * @param thatClassname
     * @param type
     *
     * @return
     *
     * @see MapperNaming
     */
    public static String with(
        MapperNaming naming, String thisClassname, String thatClassname, NamingStrategy type
    ) {
        String thisClass = getSimpleName(thisClassname);
        String thatClass = getSimpleName(thatClassname);
        final String[] prefixes = naming.trimPrefixes();
        final String[] suffixes = naming.trimSuffixes().length == 0 ? defaultSuffixes() : naming.trimSuffixes();
        String thisPrefix = "", thatPrefix = "";
        for (String prefix : prefixes) {
            if (hasText(prefix) && thisClass.startsWith(prefix)) {
                thisClass = thisClass.substring(prefix.length());
                thisPrefix = prefix;
            }
        }
        for (String prefix : prefixes) {
            if (hasText(prefix) && thatClass.startsWith(prefix)) {
                thatClass = thatClass.substring(prefix.length());
                thatPrefix = prefix;
            }
        }

        String thisSuffix = "", thatSuffix = "";
        for (String suffix : suffixes) {
            if (hasText(suffix) && thisClass.endsWith(suffix)) {
                thisClass = thisClass.substring(0, thisClass.length() - suffix.length());
                thisSuffix = suffix;
            }
        }
        for (String suffix : suffixes) {
            if (hasText(suffix) && thatClass.endsWith(suffix)) {
                thatClass = thatClass.substring(0, thatClass.length() - suffix.length());
                thatSuffix = suffix;
            }
        }

        String typeStringify = capitalize(type.name().toLowerCase());
        String namePattern = naming.pattern();
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

    private static boolean hasText(CharSequence value) {
        return value != null && value.length() > 0;
    }
}
