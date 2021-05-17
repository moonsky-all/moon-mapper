package com.moonsky.mapper;

import com.moonsky.mapper.annotation.MapperFor;

import static java.lang.Thread.currentThread;

/**
 * @author benshaoye
 */
public enum MapperUtil {
    ;

    private final static String MAPPER_FOR_NAME = MapperFor.class.getCanonicalName();

    private static Class<?> thisClass() {
        return Mappers.forName(currentThread().getStackTrace()[3].getClassName());
    }

    public static <F, T> BeanMapper<F, T> thisPrimary() {
        Class<F> fromClass = Mappers.cast(thisClass());
        MapperFor[] mapperForAll = fromClass.getAnnotationsByType(MapperFor.class);
        if (mapperForAll.length == 0) {
            throw new IllegalStateException("未知映射目标，请检查 " +
                fromClass.getCanonicalName() +
                " 是否添加注解: " +
                MAPPER_FOR_NAME);
        }
        Class<?>[] toClasses = mapperForAll[0].value();
        if (toClasses.length == 0) {
            throw new IllegalStateException("未知映射目标，请检查 " +
                fromClass.getCanonicalName() +
                " 注解: " +
                MAPPER_FOR_NAME +
                "#value() 是否包含有效值");
        }
        return get(fromClass, Mappers.cast(toClasses[0]));
    }

    public static <F, T> BeanMapper<F, T> thisMapperFor(Class<T> toClass) {
        return get(Mappers.cast(thisClass()), toClass);
    }

    public static <F, T> BeanMapper<F, T> get(Class<F> fromClass, Class<T> toClass) {
        return Mappers.getMapper(fromClass, toClass);
    }
}
