package com.moonsky.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mapper 命名策略
 *
 * @author benshaoye
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperNaming {

    /**
     * 默认命名模式
     */
    String DEFAULT_PATTERN = "{thisClass}{thisSuffix}2{thatClass}{thatSuffix}{type}";

    /**
     * 数据类的要舍弃的前缀，只舍弃第一个匹配的前缀，如
     *
     * <pre>
     * &#064;MapperNaming(trimPrefixes = {"Cy", "Cmt"})
     * &#064;MapperFor(CyCmtUserVO.class)
     * public class CyCmtUserEntity {}
     *
     * // 得到的 thisClass 为: CmtUserEntity
     * // {@link #trimSuffixes()}同理，只舍弃第一个匹配的后缀
     * </pre>
     *
     * @return 要舍弃的前缀
     */
    String[] trimPrefixes() default {};

    /**
     * 数据类的要舍弃的后缀，只舍弃第一个匹配的后缀
     *
     * @return 要舍弃的后缀
     *
     * @see com.moonsky.mapper.util.DefaultNaming#SUFFIXES 默认舍弃的后缀
     * @see #pattern() 命名模式
     */
    String[] trimSuffixes() default {};

    /**
     * 实现类命名模式
     * <p>
     * Mapper 实现会生成三个文件，类似: FromClass2ToClassCopier, ToClass2FromClassCopier, FromClass2ToClassMapper
     * <pre>
     * 这里需要指定生成文件的命名模式，这里内置的一些关键字:
     * 1. {thisClass}: 复制源类名（舍弃前缀和后缀后的名字）
     * 2. {thisPrefix}: 复制数据源类名被舍弃的前缀
     * 3. {thisSuffix}: 复制数据源类名被舍弃的后缀
     * 4. {thatClass}: 复制目标类名（舍弃前缀和后缀后的名字）
     * 5. {thatPrefix}: 复制目标数据类名被舍弃的前缀
     * 6. {thatSuffix}: 复制目标数据类名被舍弃的后缀
     * 7. {type}: Copier 或 Mapper
     *
     * 如:
     * pattern = "{thisClass}{thisSuffix}2{thatSuffix}{type}"
     * // 得到: EmployeeEntity2VOMapper
     * </pre>
     *
     * @return 命名模式名
     */
    String pattern() default DEFAULT_PATTERN;
}
