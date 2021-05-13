package com.moonsky.processing.gen;

import com.moonsky.processing.util.Element2;
import com.moonsky.processing.util.String2;
import com.moonsky.processing.util.Test2;

import javax.lang.model.element.TypeElement;
import java.util.Arrays;

/**
 * @author benshaoye
 */
enum TypeFormatter2 {
    ;

    public static String with(String template, Object... types) {
        return String2.format(template, Arrays.stream(types).map(type -> {
            if (type instanceof Class<?>) {
                return ((Class<?>) type).getCanonicalName();
            }
            if (type instanceof TypeElement) {
                return Element2.getQualifiedName((TypeElement) type);
            }
            return String.valueOf(type);
        }).toArray());
    }

    public static String defaultVal(String type) {
        if (Test2.isPrimitiveBool(type)) {
            return Boolean.FALSE.toString();
        } else if (Test2.isPrimitive(type)) {
            return "0";
        } else {
            return "null";
        }
    }
}
