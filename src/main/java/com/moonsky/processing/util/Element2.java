package com.moonsky.processing.util;

import javax.lang.model.element.*;
import javax.lang.model.type.MirroredTypeException;
import java.util.*;
import java.util.function.Function;

import static com.moonsky.processing.util.Processing2.getUtils;

/**
 * @author benshaoye
 */
public enum Element2 {
    ;

    @SuppressWarnings("all")
    public static <T> T cast(Object obj) { return (T) obj; }

    private final static Map<QualifiedNameable, String> QUALIFIED_NAME_CACHED = new IdentityHashMap<>();

    public static TypeElement getTypeElement(String classname) {
        return Processing2.getUtils().getTypeElement(classname);
    }

    @SuppressWarnings("all")
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

    public static String getQualifiedName(QualifiedNameable elem) {
        String classname = QUALIFIED_NAME_CACHED.get(elem);
        if (classname == null) {
            classname = elem.getQualifiedName().toString();
            QUALIFIED_NAME_CACHED.put(elem, classname);
        }
        return classname;
    }

    public static String getPackageName(Class<?> clazz) { return clazz.getPackage().getName(); }

    public static String getPackageName(Element elem) {
        return getQualifiedName(getUtils().getPackageOf(elem));
    }

    /**
     * 通常用来获取类名，有些类此时是不能访问
     *
     * @param t           通常是注解对象
     * @param classGetter 获取类的函数
     * @param <T>
     *
     * @return
     */
    public static <T> String getClassname(T t, Function<T, Class<?>> classGetter) {
        try {
            return classGetter.apply(t).getCanonicalName();
        } catch (MirroredTypeException mirrored) {
            return mirrored.getTypeMirror().toString();
        }
    }

    /**
     * 获取注解上使用到的 class，若不存在则返回{@link javax.lang.model.type.TypeMirror}
     *
     * @param t           注解
     * @param classGetter 方法引用
     * @param <T>         注解类
     *
     * @return class/TypeMirror
     */
    public static <T> Object getClass(T t, Function<T, Class<?>> classGetter) {
        try {
            return classGetter.apply(t);
        } catch (MirroredTypeException mirrored) {
            return mirrored.getTypeMirror();
        }
    }

    public static String[] toClassnames(Class<?>... classes) {
        if (classes == null) {
            return Const2.EMPTY_STRINGS;
        }
        String[] names = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            names[i] = classes[i].getCanonicalName();
        }
        return names;
    }

    public static String toPropertyName(ExecutableElement elem) {
        String name = elem.getSimpleName().toString();
        return String2.decapitalize(name.substring(name.startsWith(Const2.IS) ? 2 : 3));
    }

    public static String toGetterName(String field, String type) {
        return ("boolean".equals(type) ? Const2.IS : Const2.GET) + String2.capitalize(field);
    }

    public static String toSetterName(String field) {
        return Const2.SET + String2.capitalize(field);
    }

    public static List<VariableElement> getEnums(Element elem) {
        if (!Test2.isEnumClass(elem)) {
            return new ArrayList<>();
        }
        List<VariableElement> enums = new ArrayList<>();
        List<? extends Element> elements = elem.getEnclosedElements();
        for (Element element : elements) {
            if (Test2.isEnumValue(element)) {
                enums.add((VariableElement) element);
            }
        }
        return enums;
    }

    public static VariableElement findEnumAt(String classname, int index) {
        return findEnumAt(getUtils().getTypeElement(classname), index);
    }

    public static VariableElement findEnumAt(Element elem, int index) {
        if (elem == null) {
            return null;
        }
        int indexer = 0;
        for (Element child : elem.getEnclosedElements()) {
            if (Test2.isEnumValue(child)) {
                if (index == indexer) {
                    return (VariableElement) child;
                } else {
                    indexer++;
                }
            }
        }
        return null;
    }

    public static VariableElement findEnumAs(String classname, String name) {
        return findEnumAs(getUtils().getTypeElement(classname), name);
    }

    public static VariableElement findEnumAs(Element elem, String name) {
        if (elem == null) {
            return null;
        }
        for (Element child : elem.getEnclosedElements()) {
            if (Test2.isEnumValue(child) && Objects.equals(getSimpleName(child), name)) {
                return (VariableElement) child;
            }
        }
        return null;
    }
}
