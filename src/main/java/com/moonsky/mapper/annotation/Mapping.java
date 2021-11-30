package com.moonsky.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日期和时间格式化以解析字符串为日期/时间
 *
 * @author benshaoye
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Mapping {

    /**
     * 日期和时间格式化以解析字符串为日期/时间
     *
     * @return 格式化样式，如果仅为空白字符串，则视为无效
     */
    String format() default "";

    /**
     * 指定默认值
     *
     * @return
     */
    String[] defaults() default {};

    /**
     * 应用默认值的条件
     *
     * @return 默认自动推断
     */
    DefaultType defaultFor() default DefaultType.AUTO;

    enum DefaultType {
        /** 当为 null 时应用默认值 */
        NULL,
        /**
         * 当为字符串不包含任何字符（null or empty）
         * 或集合、数组不包含任何元素时（null or empty）应用默认值
         * <p>
         * 用在其他类型上报错
         */
        EMPTY,
        /**
         * 当字符串不包含任何非空字符时应用默认值
         * <p>
         * 用在其他类型上报错
         */
        BLANK,
        /**
         * 1、字符串类型，采用{@link #EMPTY}策略
         * 2、集合或数组类型采用{@link #EMPTY}策略
         * 3、其他类型上采用{@link #NULL}策略
         */
        AUTO
    }
}
