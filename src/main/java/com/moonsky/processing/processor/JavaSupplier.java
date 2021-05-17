package com.moonsky.processing.processor;

import java.util.function.Supplier;

/**
 * @author benshaoye
 */
public interface JavaSupplier extends Supplier<JavaDefinition> {

    /**
     * java 定义
     *
     * @return java 定义
     */
    @Override
    JavaDefinition get();
}
