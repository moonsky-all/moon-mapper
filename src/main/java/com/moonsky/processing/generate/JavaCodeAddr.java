package com.moonsky.processing.generate;

/**
 * @author benshaoye
 */
public interface JavaCodeAddr {

    /**
     * 添加 code
     *
     * @param addr 追加 code
     */
    void add(JavaAddr addr);

    /**
     * 返回 key
     *
     * @return key
     */
    String getKey();

    /**
     * 是否是多行代码
     *
     * @return 多行代码块要做缩进处理
     */
    default boolean isMultiply() { return false; }
}
