package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import java.util.Map;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;

/**
 * @author benshaoye
 */
public class MatchedWrapped2PrimitiveConversion extends BaseConversion implements Conversion {

    public MatchedWrapped2PrimitiveConversion() {}

    @SuppressWarnings("all")
    @Override
    public void register(ConversionRegistry registry) {
        for (Map.Entry<String, String> entry : MAP_WRAPPED_TO_PRIMITIVE.entrySet()) {
            registry.register(entry.getKey(), entry.getValue(), this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        scripts.onIfNotNull(var).scriptOf("{}.{}({})", THAT, setter.getMethodName(), var);
    }
}
