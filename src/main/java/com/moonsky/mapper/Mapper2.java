package com.moonsky.mapper;

import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.mapper.util.MapperNotFoundException;
import com.moonsky.mapper.util.NamingStrategy;

import static java.lang.Thread.currentThread;

/**
 * Mapper 映射工具访问器
 * <p>
 * <a href="https://github.com/moonsky-all/moon-mapper">
 * https://github.com/moonsky-all/moon-mapper
 * </a>
 * <p>
 * <a href="https://gitee.com/moonsky-all/moon-mapper">
 * https://gitee.com/moonsky-all/moon-mapper
 * </a>
 * <p>
 * {@code 2}与{@code tool}近音，{@code Mapper2}意为{@code Mapper}工具
 *
 * @author benshaoye
 */
public enum Mapper2 {
    ;

    private final static String MAPPER_FOR_NAME = MapperFor.class.getCanonicalName();

    private static Class<?> thisClass() {
        return Mappers.forName(currentThread().getStackTrace()[3].getClassName(), NamingStrategy.MAPPER);
    }

    /**
     * 获取当前类到主要映射目标的映射器
     * <p>
     * 当前类所注解的第一个{@link Class}值{@link MapperFor#value()}是主要映射目标
     *
     * <pre>
     * // 如这里的主要映射目标是: SecondBusinessVO.class （第一个）
     * // 获取的的是 FirstBusinessVO.class 至 SecondBusinessVO.class 的映射器
     * &#064;MapperFor(value = {SecondBusinessVO.class, ThirdBusinessVO.class})
     * public class FirstBusinessVO {
     *     // fields & getters & setters
     * }
     *
     *
     * // 这里的主要映射目标是: ThatBusinessVO.class （第一个）
     * &#064;MapperFor(value = ThatBusinessVO.class)
     * public class ThisBusinessVO {
     *     // fields & getters & setters
     * }
     * </pre>
     *
     * @param <F> 当前类
     * @param <T> 映射目标类
     *
     * @return 当前类与主要映射目标的映射器
     */
    public static <F, T> BeanMapper<F, T> thisPrimary() {
        Class<F> fromClass = Mappers.cast(thisClass());
        MapperFor[] mapperForAll = fromClass.getAnnotationsByType(MapperFor.class);
        if (mapperForAll.length == 0) {
            throw new MapperNotFoundException("未知映射目标，请检查 " + fromClass.getCanonicalName() + " 是否添加注解: " + MAPPER_FOR_NAME);
        }
        Class<?>[] toClasses = mapperForAll[0].value();
        if (toClasses.length == 0) {
            throw new MapperNotFoundException("未知映射目标，请检查 " + fromClass.getCanonicalName() + " 注解: " + MAPPER_FOR_NAME + "#value() 是否包含有效值");
        }
        return get(fromClass, Mappers.cast(toClasses[0]));
    }

    /**
     * 获取当前类到指定映射目标的映射器
     *
     * @param toClass 指定映射目标类
     * @param <F>     当前类
     * @param <T>     映射目标类
     *
     * @return 当前类与指定映射目标的映射器
     */
    public static <F, T> BeanMapper<F, T> thisMapperFor(Class<T> toClass) {
        return get(Mappers.cast(thisClass()), toClass);
    }

    /**
     * 获取指定{@code fromClass}至{@code toClass}之间的映射器
     *
     * @param fromClass 复制数据源类（被{@link MapperFor}注解的类）
     * @param toClass   映射目标类
     * @param <F>       当前类
     * @param <T>       目标类
     *
     * @return 指定类之间的映射器
     */
    public static <F, T> BeanMapper<F, T> get(Class<F> fromClass, Class<T> toClass) {
        return Mappers.getMapper(fromClass, toClass);
    }
}
