package com.moonsky.mapper.util;

/**
 * @author benshaoye
 */
public class CopierNotFoundException extends IllegalStateException {

    public CopierNotFoundException(String s) {super(s);}

    public CopierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
