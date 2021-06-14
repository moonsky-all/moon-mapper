package com.moonsky.mapper.annotation;

import java.lang.annotation.*;

/**
 * <a href="https://github.com/moonsky-all/moon-mapper">
 * https://github.com/moonsky-all/moon-mapper
 * </a>
 * <a href="https://gitee.com/moonsky-all/moon-mapper">
 * https://gitee.com/moonsky-all/moon-mapper
 * </a>
 *
 * @author benshaoye
 * @see MapperNaming Mapper/Copier 命名策略, 命名策略必须和{@link MapperFor}同时使用才生效
 */
@Repeatable(MapperFor.List.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperFor {

    /**
     * 映射目标类，被注解{@link MapperFor}以及映射目标类不能是抽象类、接口
     *
     * @return 目标类数组
     */
    Class<?>[] value();

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {

        MapperFor[] value();
    }
}
