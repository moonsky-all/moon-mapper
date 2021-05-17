package com.moonsky.mapper.annotation;

import java.lang.annotation.*;

/**
 * @author benshaoye
 */
@Repeatable(Mapping.List.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Mapping {

    String value() default "";

    Class<?> target() default void.class;

    /**
     * 忽略{@link #target()}类的{@link #value()}字段，忽略方向为{@link #ignore}
     *
     * @return 忽略模式
     */
    Mode ignore() default Mode.NONE;

    /**
     * 针对数字、日期字段到{@code String}的格式化转换
     * <p>
     * 这里采用{@code isNotBlank}判断，所以只要没有非空内容，这个字段就无效，
     * <p>
     * 如果有实际内容，两端不会{@code trim()}掉空格。
     * <p>
     * 其他类型字段自动忽略
     *
     * @return 数字或日期格式
     */
    String format() default "";

    /**
     * 默认值，当映射过程中获取到{@code null}时，将设置为默认值
     * <p>
     * 注解在不同字段类型上时，有不同要求：
     * 1. 数字字段: 基本数据类型及包装类、{@code BigDecimal}、{@code BigInteger}）；
     * 2. 枚举: 数字或枚举项名称。如果是数字就取枚举项第N项(从 0 开始)，如果是名称就取对应的枚举项；
     * 3. boolean/Boolean: 只能为 true/false
     * 4. 字符串:
     * <pre>
     * 当是字符串时应注意 null 值与普通值
     * 1. defaultValue = "null" // {@code null}
     * 2. defaultValue = "\"null\"" // "null"
     * 3. defaultValue = "123" // "123"
     * 3. defaultValue = "\"123\"" // "123"
     *
     * 解释：
     * 首尾如果有双引号认为是用来包裹字符串的，不被认为是字符串的一部分
     * 如果没有会自动加上首尾的双引号
     * </pre>
     *
     * @return 字段默认值
     */
    String defaultValue() default "";

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    @interface List {

        Mapping[] value() default {};
    }
}
