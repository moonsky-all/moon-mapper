package com.moonsky.mapper;

import com.moonsky.mapper.annotation.MapperFor;

/**
 * @author benshaoye
 */
public enum MapperUtil {
    ;

    private final static String MAPPER_FOR_NAME = MapperFor.class.getCanonicalName();

    private static Class<?> thisClass() {
        try {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            StackTraceElement traceElement = elements[elements.length - 3];
            return Class.forName(traceElement.getClassName());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public static <F, T> BeanMapper<F, T> thisPrimary() {
        Class<F> fromClass = (Class<F>) thisClass();
        MapperFor[] mapperForAll = fromClass.getAnnotationsByType(MapperFor.class);
        if (mapperForAll.length == 0) {
            throw new IllegalStateException("未知映射目标，请检查 " + fromClass.getCanonicalName() + " 是否添加注解: " + MAPPER_FOR_NAME);
        }
        Class<?>[] toClasses = mapperForAll[0].value();
        if (toClasses.length == 0) {
            throw new IllegalStateException("未知映射目标，请检查 " + fromClass.getCanonicalName() + " 注解: " + MAPPER_FOR_NAME + "#value() 是否包含有效值");
        }
        return get(fromClass, (Class<T>) toClasses[0]);
    }

    public static <F, T> BeanMapper<F, T> thisMapperFor(Class<T> toClass) {
        return get((Class<F>) thisClass(), toClass);
    }

    public static <F, T> BeanMapper<F, T> get(Class<F> fromClass, Class<T> toClass) {
        throw new UnsupportedOperationException();
    }
}
