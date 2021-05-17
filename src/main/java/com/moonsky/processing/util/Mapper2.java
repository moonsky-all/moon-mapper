package com.moonsky.processing.util;

import com.moonsky.mapper.annotation.MapperFor;

/**
 * @author benshaoye
 */
public enum Mapper2 {
    ;

    public static String trim(MapperFor mapperFor, String simpleName) {
        for (String prefix : mapperFor.trimNamePrefix()) {
            if (simpleName.startsWith(prefix)) {
                simpleName = simpleName.substring(prefix.length());
                break;
            }
        }
        for (String suffix : mapperFor.trimNameSuffix()) {
            if (simpleName.endsWith(suffix)) {
                simpleName = simpleName.substring(0, simpleName.length() - suffix.length());
            }
        }
        return simpleName;
    }
}
