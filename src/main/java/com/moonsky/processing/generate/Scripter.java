package com.moonsky.processing.generate;

/**
 * @author benshaoye
 */
public interface Scripter {

    /**
     * 语句内容
     *
     * @return
     */
    String getLineScript();

    /**
     * 应用为非有序语句
     */
    void withUnsorted();

    /**
     * 应用为不可用状态
     */
    void withUnavailable();

    /**
     * 单行长度
     *
     * @return 长度
     */
    default int length() {
        String line = getLineScript();
        return line == null ? 0 : line.length();
    }

    /**
     * 简单插入
     *
     * @param addr
     */
    default void addOnSimplify(JavaAddr addr) {
        addr.add(getLineScript());
    }

    /**
     * 多行插入
     *
     * @param addr
     */
    default void addOnMultiply(JavaAddr addr) {
        addr.newLine(getLineScript());
    }

    /**
     * 是否是有序的，一般语句要保持声明顺序，以保证语句不出错
     * <p>
     * 但有些语句可以没有作用域上下文依赖，可以不用保持顺序
     *
     * @return 是否有序
     */
    default boolean isSorted() { return true; }

    /**
     * 是否可用
     *
     * @return
     */
    default boolean isAvailable() { return true; }
}
