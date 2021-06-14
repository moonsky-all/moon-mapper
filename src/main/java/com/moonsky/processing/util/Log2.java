package com.moonsky.processing.util;

import javax.tools.Diagnostic;
import java.util.concurrent.atomic.AtomicBoolean;
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
    private static final AtomicBoolean STARTED = new AtomicBoolean(true);

    private static void messageOf(Diagnostic.Kind kind, String message) {
        if (STARTED.get()) {
            getMessager().printMessage(kind, ">> " + INDEXER.getAndIncrement() + " " + message);
        }
    }

    public static void start() { STARTED.set(true); }

    public static void end() { STARTED.set(false); }

    public static void warn(Object message) { messageOf(WARNING, String.valueOf(message)); }

    public static void warn(String message, Object... values) {
        messageOf(WARNING, String2.format(message, values));
    }

    public static void println(String message, Object... values) {
        messageOf(MANDATORY_WARNING, String2.format(message, values));
    }

    public static void printlnStackTrace() { printlnStackTrace(5); }

    public static void printlnStackTrace(int deep) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 2; i < elements.length && i < deep; i++) {
            StackTraceElement trace = elements[i];
            Log2.warn("{} # {}", trace.getClassName(), trace.getMethodName(), trace.getLineNumber());
        }
    }
}
