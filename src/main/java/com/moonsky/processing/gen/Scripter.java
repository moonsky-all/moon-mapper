package com.moonsky.processing.gen;

/**
 * @author benshaoye
 */
public interface Scripter {

    /**
     * 单行长度
     *
     * @return 长度
     */
    int length();

    /**
     * 应用为非有序语句
     */
    void withUnsorted();

    /**
     * 简单插入
     *
     * @param addr
     */
    void addOnSimplify(JavaAddr addr);

    /**
     * 多行插入
     *
     * @param addr
     */
    void addOnMultiple(JavaAddr addr);

    /**
     * 是否是有序的，一般语句要保持声明顺序，以保证语句不出错
     * <p>
     * 但有些语句可以没有作用域上下文依赖，可以不用保持顺序
     *
     * @return 是否有序
     */
    default boolean isSorted() { return true; }
}
