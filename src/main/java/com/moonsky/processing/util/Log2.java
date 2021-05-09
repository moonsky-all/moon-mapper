package com.moonsky.processing.util;

import javax.tools.Diagnostic;
import java.util.concurrent.atomic.AtomicInteger;

import static com.moonsky.processing.util.Processing2.getMessager;
import static javax.tools.Diagnostic.Kind.MANDATORY_WARNING;
import static javax.tools.Diagnostic.Kind.WARNING;

/**
 * @author benshaoye
 */
public enum Log2 {
    ;
    private final static AtomicInteger INDEXER = new AtomicInteger();

    private static void messageOf(Diagnostic.Kind kind, String message) {
        getMessager().printMessage(kind, ">> " + INDEXER.getAndIncrement() + " " + message);
    }

    public static void warn(Object message) { messageOf(WARNING, String.valueOf(message)); }

    public static void warn(String message, Object... values) {
        messageOf(WARNING, String2.format(message, values)); }

    public static void println(String message, Object... values) {
        messageOf(MANDATORY_WARNING, String2.format(message, values));
    }
}
