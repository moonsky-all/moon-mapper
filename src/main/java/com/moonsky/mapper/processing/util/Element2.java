package com.moonsky.mapper.processing.util;

import com.moonsky.mapper.processing.holder.Holders;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import java.util.List;
import java.util.function.Function;

/**
 * @author benshaoye
 */
public enum Element2 {
    ;

    private static ProcessingEnvironment getEnv() {
        return Holders.INSTANCE.getEnvironment();
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

    public static String getSimpleName(Class<?> clazz) { return clazz.getSimpleName(); }

    public static String getSimpleName(Element elem) { return elem.getSimpleName().toString(); }

    public static String getQualifiedName(Class<?> elem) { return elem.getCanonicalName(); }

    public static String getQualifiedName(QualifiedNameable elem) { return elem.getQualifiedName().toString(); }

    public static String getPackageName(Class<?> clazz) { return clazz.getPackage().getName(); }

    public static String getPackageName(Element elem) {
        return getQualifiedName(Processing2.getUtils().getPackageOf(elem));
    }

    public static <T> String getClassname(T t, Function<T, Class<?>> classGetter) {
        try {
            return classGetter.apply(t).getCanonicalName();
        } catch (MirroredTypeException mirrored) {
            return mirrored.getTypeMirror().toString();
        }
    }

    public static List<VariableElement> getEnums(Element elem) {

    }
}
