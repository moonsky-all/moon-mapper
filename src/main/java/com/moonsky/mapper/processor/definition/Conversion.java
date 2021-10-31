package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * 应该策略化默认代码转换，但是策略化有点问题就是不好做到不同类型之间的互斥
 *
 * @author benshaoye
 */
public interface Conversion {

    /**
     * 注册转换器
     *
     * @param registry
     */
    void register(ConversionRegistry registry);

    /**
     * 执行{@code Mapping}动作
     *
     * @param scripts
     * @param getter
     * @param setter
     */
    void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    );
}
