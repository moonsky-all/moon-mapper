package com.moonsky.mapper;

import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.mapper.util.Keyword;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author benshaoye
 */
public enum Mappers {
    ;
    private final static Map<String, BeanMapper<?, ?>> MAPPER_MAP = new ConcurrentHashMap<>();
    private final static Map<String, BeanCopier<?, ?>> COPIER_MAP = new ConcurrentHashMap<>();

    static Class<?> forName(String classname) {
        try {
            return Class.forName(classname);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String keyOf(Class<?> fromClass, Class<?> toClass) {
        return String.join(":", fromClass.getCanonicalName(), toClass.getCanonicalName());
    }

    public static <F, T> BeanMapper<F, T> getMapper(Class<F> fromClass, Class<T> toClass) {
        final String cachedKey = keyOf(fromClass, toClass);
        BeanMapper<?, ?> mapper = MAPPER_MAP.get(cachedKey);
        if (mapper == null) {
            mapper = load(Keyword.MAPPER, fromClass, toClass);
            MAPPER_MAP.put(cachedKey, mapper);
        }
        return cast(mapper);
    }

    public static <F, T> BeanCopier<F, T> getCopier(Class<F> fromClass, Class<T> toClass) {
        final String cachedKey = keyOf(fromClass, toClass);
        BeanCopier<?, ?> copier = COPIER_MAP.get(cachedKey);
        if (copier == null) {
            copier = load(Keyword.COPIER, fromClass, toClass);
            COPIER_MAP.put(cachedKey, copier);
        }
        return cast(copier);
    }

    private static <T> T load(
        Keyword keyword, Class<?> fromClass, Class<?> toClass
    ) {
        MapperFor targetMapperFor = null;
        MapperFor[] mapperForAll = fromClass.getAnnotationsByType(MapperFor.class);
        for (int i = mapperForAll.length - 1; i > -1; i--) {
            targetMapperFor = mapperForAll[i];
            for (Class<?> aClass : targetMapperFor.value()) {
                if (aClass == toClass) {
                    break;
                }
            }
        }
        if (targetMapperFor == null) {
            throw new IllegalStateException("未知映射信息");
        }
        String packageName = Keyword.getPackageName(fromClass);
        String simpleName = Keyword.with(targetMapperFor, fromClass, toClass, keyword);
        String classname = String.join(".", packageName, simpleName);
        try {
            return cast(forName(classname).newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Mapper|Copier 实例化异常", e);
        }
    }

    @SuppressWarnings("all")
    static <T> T cast(Object value) { return (T) value; }
}
