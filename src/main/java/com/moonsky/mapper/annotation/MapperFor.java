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

    /**
     * 数据类的要舍弃的前缀，只舍弃第一个匹配的前缀，如
     *
     * <pre>
     * &#064;MapperFor(trimNamePrefix = {"Cy", "Cmt"})
     * public class CyCmtUserEntity {}
     *
     * // 得到的 thisClass 为: CmtUserEntity
     * // {@link #trimNameSuffix()}同理，只舍弃第一个匹配的后缀
     * </pre>
     *
     * @return 要舍弃的前缀
     */
    String[] trimNamePrefix() default {};

    /**
     * 数据类的要舍弃的后缀，只舍弃第一个匹配的后缀
     *
     * @return 要舍弃的后缀
     */
    String[] trimNameSuffix() default {};

    /**
     * 实现类命名模式
     * <p>
     * Mapper 实现会生成三个文件，类似: FromClass2ToClassCopier, ToClass2FromClassCopier, FromClass2ToClassMapper
     * <pre>
     * 这里需要指定生成文件的命名模式，其中包括三个用花括号包裹的关键字:
     * 1. {thisClass}: 复制源类名（舍弃前缀和后缀后的名字）
     * 2. {thisPrefix}: 复制数据源类名被舍弃的前缀
     * 3. {thisSuffix}: 复制数据源类名被舍弃的后缀
     * 4. {thatClass}: 复制目标类名
     * 5. {thatPrefix}: 复制目标数据类名被舍弃的前缀
     * 6. {thatSuffix}: 复制目标数据类名被舍弃的后缀
     * 7. {type}: Copier 或 Mapper
     *
     * 如:
     * namePattern = "{thatClass}By{thisClass}{type}"
     * </pre>
     *
     * @return 命名模式名
     */
    String namePattern() default "{thisClass}2{thatClass}{type}";

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {

        MapperFor[] value();
    }
}
