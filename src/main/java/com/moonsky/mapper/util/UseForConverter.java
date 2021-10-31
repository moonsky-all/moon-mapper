package com.moonsky.mapper.util;

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
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UseForConverter {

    /**
     * 标记当前类里只有一个入参且返回值类型不是{@code void}的公共静态方法是否是用作转换器
     * <p>
     * 或当前被注解的方法是否用作转换器
     *
     * @return 默认情况下，符合条件的都是
     */
    boolean value() default true;

    /**
     * 如果用在类上，是否抑制加载父类的转换器方法检查
     * <p>
     * 如果指定为{@code false}将会舍弃父类以及所实现接口中的所有方法
     *
     * @return 默认同时加载自身类和父类所有符合条件的方法
     */
    boolean suppressSuper() default false;
}
