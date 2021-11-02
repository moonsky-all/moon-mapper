package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import java.util.Map;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.THIS;

/**
 * @author benshaoye
 */
public class MatchedPrimitive2WrappedConversion extends BaseConversion implements Conversion {

    public MatchedPrimitive2WrappedConversion() {}

    @SuppressWarnings("all")
    @Override
    public void register(ConversionRegistry registry) {
        for (Map.Entry<String, String> entry : MAP_PRIMITIVE_TO_WRAPPED.entrySet()) {
            registry.register(entry.getKey(), entry.getValue(), this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
    }
}
