package com.moonsky.mapper;

import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.mapper.util.CopierNotFoundException;
import com.moonsky.mapper.util.MapperNotFoundException;
import com.moonsky.mapper.util.NamingStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 写这个类的目的是为了把一些方法封装起来
 *
 * @author benshaoye
 */
public enum Mappers {
    ;
    private final static Map<NamingKey, BeanMapper<?, ?>> MAPPER_MAP = new HashMap<>();
    private final static Map<NamingKey, BeanCopier<?, ?>> COPIER_MAP = new HashMap<>();

    private static void cacheMapper(NamingKey key, BeanMapper<?, ?> mapper) {
        synchronized (MAPPER_MAP) {
            MAPPER_MAP.put(key, mapper);
        }
    }

    private static void cacheCopier(NamingKey key, BeanCopier<?, ?> copier) {
        synchronized (COPIER_MAP) {
            COPIER_MAP.put(key, copier);
        }
    }

    static Class<?> forName(String classname, NamingStrategy keyword) {
        try {
            return Class.forName(classname);
        } catch (ClassNotFoundException e) {
            throw keyword.newException(classname, e);
        }
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
     *
     * @throws MapperNotFoundException 不存在指定映射器
     */
    public static <F, T> BeanMapper<F, T> getMapper(Class<F> fromClass, Class<T> toClass) {
        final NamingKey cachedKey = NamingKey.of(fromClass, toClass);
        BeanMapper<?, ?> mapper = MAPPER_MAP.get(cachedKey);
        if (mapper == null) {
            mapper = load(NamingStrategy.MAPPER, fromClass, toClass, false);
            cacheMapper(cachedKey, mapper);
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
     *
     * @throws CopierNotFoundException 不存在指定复制器
     */
    public static <F, T> BeanCopier<F, T> getCopier(Class<F> fromClass, Class<T> toClass) {
        final NamingKey cachedKey = NamingKey.of(fromClass, toClass);
        BeanCopier<?, ?> copier = COPIER_MAP.get(cachedKey);
        if (copier == null) {
            copier = load(NamingStrategy.COPIER, fromClass, toClass, false);
            cacheCopier(cachedKey, copier);
        }
        return cast(copier);
    }

    static <F, T> BeanCopier<? super F, ? super T> inferCopier(Class<F> fromClass, Class<T> toClass) {
        return getCopier(fromClass, toClass);
    }

    static <F, T> BeanMapper<? super F, ? super T> inferMapper(Class<F> fromClass, Class<T> toClass) {
        return getMapper(fromClass, toClass);
    }

    private static <T> T inferLoad(NamingStrategy keyword, Class<?> fromClass, Class<?> toClass) {
        return null;
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
            throw keyword.newException("未知映射信息, 请检查" +//
                annotatedClass.getCanonicalName() +//
                "类是否被 MapperFor 注解，且包含有效值：" +//
                targetClass.getCanonicalName());
        }
        MapperNaming naming = annotatedClass.getAnnotation(MapperNaming.class);
        String packageName = NamingStrategy.getPackageName(fromClass);
        String simpleName = NamingStrategy.with(naming, fromClass, toClass, keyword);
        try {
            return cast(forName(packageName + "." + simpleName, keyword).newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            String type = NamingStrategy.capitalize(keyword.name().toLowerCase());
            throw keyword.newException(type + " 实例化异常", e);
        }
    }

    @SuppressWarnings("all")
    static <T> T cast(Object value) {return (T) value;}

    private static final class NamingKey {

        private final Class<?> fromClass;
        private final Class<?> thatClass;
        private final int hashValue;

        public NamingKey(Class<?> fromClass, Class<?> thatClass) {
            this.hashValue = (Objects.hashCode(fromClass) + Objects.hashCode(thatClass) + 1) * 31;
            this.fromClass = fromClass;
            this.thatClass = thatClass;
        }

        public static NamingKey of(Class<?> fromClass, Class<?> thatClass) {
            return new NamingKey(fromClass, thatClass);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NamingKey) {
                NamingKey key = (NamingKey) o;
                return (fromClass == key.fromClass) && (thatClass == key.thatClass);
            }
            return false;
        }

        @Override
        public int hashCode() {return hashValue;}
    }
}
