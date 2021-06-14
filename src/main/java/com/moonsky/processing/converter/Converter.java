package com.moonsky.processing.converter;

import com.moonsky.processing.declared.PropertyMethodDeclared;
import com.moonsky.processing.generate.JavaCodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public interface Converter {

    BaseConverter ENUM = new EnumFromStringConverter();

    /**
     * 执行转换
     *
     * @param scripts 代码块
     * @param setter  setter
     * @param getter  getter
     */
    void doConvert(
        JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    );
}
