package com.moonsky.processing.util;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author benshaoye
 */
public class Importer {

    private final static Map<String, String> GENERICS_MAP = new HashMap<>();

    private final static String EMPTY = "", VARARGS = "...", DOT = ".", LANG_PACKAGE = "java.lang";

    private final Map<String, String> shortNameCached = new HashMap<>();
    private final Map<String, String> importCached = new TreeMap<>();
    private final String packageName;

    public Importer() { this(null); }

    public Importer(String packageName) { this.packageName = packageName; }

    public String onImported(Class<?> classname) { return onImported(classname.getCanonicalName()); }

    public String onImported(TypeElement element) { return onImported(element.asType()); }

    public String onImported(TypeMirror mirror) { return onImported(mirror.toString()); }

    public String onImported(String classname) {
        String shortName = shortNameCached.get(classname);
        if (shortName != null) {
            return shortName;
        }
        int length = classname.length();
        StringBuilder result = new StringBuilder();
        StringBuilder cache = new StringBuilder();

        boolean prevCharIsBlank = false;
        for (int i = 0; i < length; i++) {
            char ch = classname.charAt(i);
            switch (ch) {
                case '<':
                case '>':
                case ',':
                case '[':
                case ']':
                    result.append(doImported(cache.toString()));
                    cache.setLength(0);
                    result.append(ch);
                    if (ch == ',') {
                        result.append(' ');
                    }
                    prevCharIsBlank = false;
                    break;
                case '?':
                case '&':
                    result.append(ch);
                    prevCharIsBlank = false;
                    break;
                case ' ':
                    if (!prevCharIsBlank) {
                        prevCharIsBlank = true;
                        cache.append(ch);
                    }
                    break;
                default:
                    prevCharIsBlank = false;
                    cache.append(ch);
            }
        }

        if (cache.length() > 0) {
            result.append(doImported(cache.toString()));
        }

        return result.toString();
    }

    private String doImported(String fullName) {
        if (VARARGS.equals(fullName)) {
            return fullName;
        }
        if (fullName.endsWith(VARARGS)) {
            return withImported(fullName.replace(VARARGS, "")) + VARARGS;
        }
        return withImported(fullName);
    }

    private String withImported(String fullName) {
        if (String2.isEmpty(fullName)) {
            return EMPTY;
        }
        if (String2.isBlank(fullName)) {
            return " ";
        }
        String shortName = shortNameCached.get(fullName);
        if (shortName != null) {
            return shortName;
        }
        shortName = Element2.getSimpleName(fullName);
        if (isPackageOf(fullName, shortName, packageName)) {
            shortNameCached.put(fullName, shortName);
            return shortName;
        }
        String imported = importCached.get(shortName);
        if (String2.isNotBlank(imported) && !Objects.equals(imported, fullName)) {
            return fullName;
        } else if (Test2.isPrimitiveClass(fullName) || Test2.isVoid(fullName)) {
            shortNameCached.put(fullName, fullName);
            return fullName;
        } else {
            if (fullName.indexOf(Const2.DOT) < 0) {
                if (GENERICS_MAP.containsKey(fullName)) {
                    return shortName;
                }
                if (Processing2.getUtils().getTypeElement(fullName) == null) {
                    GENERICS_MAP.put(fullName, shortName);
                    return shortName;
                }
                importCached.put(shortName, fullName.trim());
            } else if (!isPackageOf(fullName, shortName, LANG_PACKAGE)) {
                importCached.put(shortName, fullName.trim());
            }
            shortNameCached.put(fullName, shortName);
            return shortName;
        }
    }

    private static boolean isPackageOf(String fullName, String shortName, String expectedPackage) {
        return (expectedPackage.endsWith(DOT) ? (expectedPackage + shortName)
            : (expectedPackage + DOT + shortName)).equals(fullName);
    }

    @Override
    public String toString() { return toString(EMPTY); }

    public String toString(String delimiter) {
        return importCached.values()
            .stream()
            .map(name -> "import " + name + ";")
            .collect(Collectors.joining(delimiter));
    }
}
