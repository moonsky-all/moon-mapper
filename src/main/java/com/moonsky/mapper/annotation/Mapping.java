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
     * 注解在复合数据类、简单数据集合、简单数据数组等类上，表示此属性期望进行深度映射
     * <p>
     * 简单数据: 即常见的 String、Integer、Date 等 jdk 提供的基础数据类型
     * 复合数据: 即常见的实体、VO、DTO、DO 等由简单数据类、基本数据类型构成的数据结构
     * 简单数据集合: 简单数据类型组成的集合
     * 简单数据数组: 简单数据集合的基础上增加 int、long 等基本数据类型
     * <p>
     * 若注解在复合数据集合/数组也只会进行一层深度映射，不会进一步将复合数据深度映射
     *
     * @see Deep2
     */
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    @interface Deep {}

    /**
     * 注解在复合数据集合/数组上，表示不仅要将集合/数组深度映射
     * 而且构成集合/数组项的复合数据也要进行深度映射
     * <p>
     * 注解在单纯复合数据、简单数据集合/数组上时，与{@link Deep}等效
     * (即大多数情况下 Deep 支持的特性 Deep2 都支持)
     *
     * @see Deep
     */
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    @interface Deep2 {}

    /**
     * 将此字段视为复合数据，并将复合数据的各字段以{@link Flat#value()}为
     * 前缀，映射为目标对象的多个字段
     * <p>
     * 如:
     *
     * <pre>
     * public class Foo {
     *
     *     private String name;
     *
     *     private int age;
     * }
     *
     * public class User {
     *
     *     &#64;Mapping.Flat
     *     private Foo foo;
     *
     *     &#64;Mapping.Flat("user")
     *     private Foo bar;
     * }
     *
     * public class UserInfo {
     *
     *     // User.foo.name
     *     private String fooName;
     *
     *     // User.foo.age
     *     private int fooAge;
     *
     *     // User.bar.name
     *     private String userName;
     *
     *     // User.bar.age
     *     private int userAge;
     * }
     * </pre>
     */
    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    @interface Flat {

        /**
         * 展平前缀，默认是被注解的复合字段名
         *
         * @return 前缀
         */
        String value() default "";
    }
}
