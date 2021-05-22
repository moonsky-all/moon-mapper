package com.moonsky.processing.generate;

import com.moonsky.processing.util.String2;
import com.moonsky.processing.util.Test2;

/**
 * @author benshaoye
 */
enum TypeFormatter2 {
    ;

    public static String with(String template, Object... types) {
        return String2.typeFormatted(template, types);
    }

    public static String defaultVal(String type) {
        if (Test2.isPrimitiveBoolClass(type)) {
            return Boolean.FALSE.toString();
        } else if (Test2.isPrimitiveClass(type)) {
            return "0";
        } else {
            return "null";
        }
    }
}
