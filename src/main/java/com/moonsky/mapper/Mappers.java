package com.moonsky.mapper;

import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.mapper.util.DefaultNaming;
import com.moonsky.mapper.util.NamingStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 写这个类的目的是为了把一些方法封装起来
 *
 * @author benshaoye
 */
public enum Mappers {
    ;
    private final static Map<String, BeanMapper<?, ?>> MAPPER_MAP = new ConcurrentHashMap<>();
    private final static Map<String, BeanCopier<?, ?>> COPIER_MAP = new ConcurrentHashMap<>();

    static Class<?> forName(String classname, NamingStrategy keyword) {
        try {
            return Class.forName(classname);
        } catch (ClassNotFoundException e) {
            throw keyword.newException(classname, e);
        }
    }

    private static String keyOf(Class<?> fromClass, Class<?> toClass) {
        return String.join(":", fromClass.getCanonicalName(), toClass.getCanonicalName());
    }

    /**
     * 实际获取{@code Mapper}的方法
     *
     * @param fromClass 映射数据源类
     * @param toClass   目标类
     * @param <F>       源类型
     * @param <T>       目标类型
     *
     * @return 源类型至目标类型的映射器
     */
    public static <F, T> BeanMapper<F, T> getMapper(Class<F> fromClass, Class<T> toClass) {
        final String cachedKey = keyOf(fromClass, toClass);
        BeanMapper<?, ?> mapper = MAPPER_MAP.get(cachedKey);
        if (mapper == null) {
            mapper = load(NamingStrategy.MAPPER, fromClass, toClass, false);
            MAPPER_MAP.put(cachedKey, mapper);
        }
        return cast(mapper);
    }

    /**
     * 实际获取{@code Copier}的方法
     *
     * @param fromClass 复制数据源类
     * @param toClass   目标类
     * @param <F>       源类型
     * @param <T>       目标类型
     *
     * @return 源类型至目标类型的复制器
     */
    public static <F, T> BeanCopier<F, T> getCopier(Class<F> fromClass, Class<T> toClass) {
        final String cachedKey = keyOf(fromClass, toClass);
        BeanCopier<?, ?> copier = COPIER_MAP.get(cachedKey);
        if (copier == null) {
            copier = load(NamingStrategy.COPIER, fromClass, toClass, false);
            COPIER_MAP.put(cachedKey, copier);
        }
        return cast(copier);
    }

    private static <T> T load(
        NamingStrategy keyword, Class<?> fromClass, Class<?> toClass, boolean reverse
    ) {
        MapperFor targetMapperFor = null;
        Class<?> targetClass = reverse ? fromClass : toClass;
        Class<?> annotatedClass = reverse ? toClass : fromClass;
        MapperFor[] mapperForAll = annotatedClass.getAnnotationsByType(MapperFor.class);
        for (int i = mapperForAll.length - 1; i > -1; i--) {
            MapperFor temp = mapperForAll[i];
            for (Class<?> aClass : temp.value()) {
                if (aClass == targetClass) {
                    targetMapperFor = temp;
                    break;
                }
            }
        }
        if (targetMapperFor == null) {
            throw keyword.newException("未知映射信息, 请检查" +
                annotatedClass.getCanonicalName() +
                "类是否被 MapperFor 注解，且包含有效值：" +
                targetClass.getCanonicalName());
        }
        MapperNaming naming = DefaultNaming.defaultIfNull(annotatedClass.getAnnotation(MapperNaming.class));
        String packageName = NamingStrategy.getPackageName(fromClass);
        String simpleName = NamingStrategy.with(naming, fromClass, toClass, keyword);
        String classname = String.join(".", packageName, simpleName);
        try {
            return cast(forName(classname, keyword).newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            String type = NamingStrategy.capitalize(keyword.name().toLowerCase());
            throw keyword.newException(type + " 实例化异常", e);
        }
    }

    @SuppressWarnings("all")
    static <T> T cast(Object value) { return (T) value; }
}
