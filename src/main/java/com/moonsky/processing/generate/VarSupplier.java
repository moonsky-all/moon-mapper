package com.moonsky.processing.generate;

/**
 * @author benshaoye
 */
public interface VarSupplier<T> {

    /**
     * 变量名
     *
     * @return var
     */
    String nextVar();

    /**
     * 变量名
     *
     * @return const var
     */
    String nextConstVar();

    /**
     * 变量名
     *
     * @param key 如果 key 对应有 var，就返回否则就构造并缓存
     *
     * @return var
     */
    String nextVar(Object key);

    /**
     * 变量名
     *
     * @param key 如果 key 对应有 var，就返回否则就构造并缓存
     *
     * @return const var
     */
    String nextConstVar(Object key);

    /**
     * 定义字段
     *
     * @param fieldName         字段名
     * @param fieldTypeTemplate 字段类型
     * @param types             类型
     *
     * @return
     */
    T declareField(String fieldName, String fieldTypeTemplate, Object... types);

    /**
     * 是否包含指定名称字段
     *
     * @param fieldName 字段名
     *
     * @return 如果已定义字段名就返回 true，否则返回 false
     */
    boolean contains(String fieldName);
}
