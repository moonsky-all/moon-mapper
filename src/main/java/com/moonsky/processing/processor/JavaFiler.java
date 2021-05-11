package com.moonsky.processing.processor;

import com.moonsky.processing.holder.Holders;

import javax.annotation.processing.Filer;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author benshaoye
 */
public enum JavaFiler {
    ;

    private final static byte[] BYTES = {};
    private final static Map<String, Object> writtenJava = new ConcurrentHashMap<>();

    private static boolean isWritable(String classname) {
        // 已经生成过的不重新生成
        if (writtenJava.containsKey(classname)) {
            return false;
        }
        // 已经存在的不生成
        if (Holders.INSTANCE.getUtils().getTypeElement(classname) != null) {
            writtenJava.put(classname, BYTES);
            return false;
        }
        writtenJava.put(classname, BYTES);
        return true;
    }

    private static Filer getFiler() {
        return Holders.INSTANCE.getEnvironment().getFiler();
    }

    public static void write(JavaDefinition definition) {
        if (definition != null && isWritable(definition.getClassname())) {
            try (Writer jw = getFiler().createSourceFile(definition.getClassname()).openWriter();
                PrintWriter writer = new PrintWriter(jw)) {
                writer.write(definition.toString());
            } catch (/* IOException */Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
