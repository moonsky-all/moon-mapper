package com.moonsky.mapper.util;

/**
 * @author benshaoye
 */
public class MapperNotFoundException extends RuntimeException {

    public MapperNotFoundException(String s) {super(s);}

    public MapperNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
