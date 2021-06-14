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
}
