package com.moonsky.mapper.annotation;

import java.lang.annotation.*;

/**
 * @author benshaoye
 */
@Repeatable(MapperFor.List.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperFor {

    Class<?>[] value();

    String namePattern() default "{thisClass}2{thatClass}{type}";

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {

        MapperFor[] value();
    }
}
