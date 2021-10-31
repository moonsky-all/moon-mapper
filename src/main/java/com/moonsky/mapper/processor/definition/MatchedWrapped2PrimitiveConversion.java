package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

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
        registry.register(CLASS_Character, PRIMITIVE_char, this);
        registry.register(CLASS_Double, PRIMITIVE_double, this);
        registry.register(CLASS_Float, PRIMITIVE_float, this);
        registry.register(CLASS_Long, PRIMITIVE_long, this);
        registry.register(CLASS_Integer, PRIMITIVE_int, this);
        registry.register(CLASS_Short, PRIMITIVE_short, this);
        registry.register(CLASS_Byte, PRIMITIVE_byte, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        scripts.onIfNotNull(var).scriptOf("{}.{}({})", THAT, setter.getMethodName(), var);
    }
}
