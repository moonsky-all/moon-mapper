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

    T declareField(String fieldName, String fieldTypeTemplate, Object... types);
}
