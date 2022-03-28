package com.moonsky.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记类或方法是否是转换器
 * <p>
 * 默认情况下类里的所有只有一个入参、且有返回值的公共静态方法都是转换器
 * <p>
 * 如果某些符合条件的需要确认不是转换器的可使用此注解达到目的
 *
 * @author benshaoye
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Converter {

    /**
     * 限制一个静态方法用为转换器
     *
     * @return 禁用一个方法为转换器
     */
    boolean disabled() default false;

    /**
     * 注解在类上，标记该类及其所有父类、接口的静态方法
     * 均被加载为转换器方法
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface AllStaticMethods {

        /**
         * 是否抑制加载父类的转换器方法检查
         * <p>
         * 如果指定为{@code false}将会舍弃父类以及所实现接口中的所有方法
         *
         * @return 默认同时加载自身类和父类所有符合条件的方法
         */
        boolean superDisabled() default false;

        /**
         * 自定义静态方法转换器是否自行处理 null 值，
         * 默认不需要自行处理，即传给转换器的数据均不为 null
         * @return true/false
         */
        boolean handleNull() default false;
    }
}
