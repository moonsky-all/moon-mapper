package com.moonsky.mapper;

import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.mapper.util.CopierNotFoundException;
import com.moonsky.mapper.util.MapperNotFoundException;
import com.moonsky.mapper.util.NamingStrategy;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
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

    private final static Unsafe UNSAFE;
    private final static Map<NamingKey, BeanMapper<?, ?>> MAPPER_MAP = new HashMap<>();
    private final static Map<NamingKey, BeanCopier<?, ?>> COPIER_MAP = new HashMap<>();

    static {
        Unsafe unsafe = null;
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception ignored) {}
        UNSAFE = unsafe;
    }

    public static <T> T create(Class<T> klass) {
        try {
            return (T) UNSAFE.allocateInstance(klass);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    public static final String CONST = "CONST";

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

    static Class<?> forName(String classname, NamingStrategy strategy) {
        try {
            return Class.forName(classname);
        } catch (ClassNotFoundException e) {
            throw strategy.newException(classname, e);
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
            mapper = load(NamingStrategy.MAPPER, fromClass, toClass);
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
            copier = load(NamingStrategy.COPIER, fromClass, toClass);
            cacheCopier(cachedKey, copier);
        }
        return cast(copier);
    }

    /**
     * 加载指定类型映射器/复制器
     *
     * @param strategy  映射器类型
     * @param fromClass 源类
     * @param toClass   目标类
     * @param <T>       映射器类型
     *
     * @return 指定映射器
     *
     * @throws MapperNotFoundException 不存在指定映射器时抛出
     * @throws CopierNotFoundException 不存在指定复制器时抛出
     */
    private static <T> T load(NamingStrategy strategy, Class<?> fromClass, Class<?> toClass) {
        String packageName = NamingStrategy.getPackageName(fromClass);
        MapperNaming naming = fromClass.getAnnotation(MapperNaming.class);
        String simpleName = NamingStrategy.with(naming, fromClass, toClass, strategy);
        try {
            return cast(forName(packageName + "." + simpleName, strategy).getField(CONST).get(null));
        } catch (Exception e) {
            String type = NamingStrategy.capitalize(strategy.name().toLowerCase());
            throw strategy.newException(type + " 映射器不存在", e);
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
