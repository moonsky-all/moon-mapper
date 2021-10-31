package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

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
        registry.register(PRIMITIVE_boolean, CLASS_Boolean, this);
        registry.register(PRIMITIVE_char, CLASS_Character, this);
        registry.register(PRIMITIVE_double, CLASS_Double, this);
        registry.register(PRIMITIVE_float, CLASS_Float, this);
        registry.register(PRIMITIVE_long, CLASS_Long, this);
        registry.register(PRIMITIVE_int, CLASS_Integer, this);
        registry.register(PRIMITIVE_short, CLASS_Short, this);
        registry.register(PRIMITIVE_byte, CLASS_Byte, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
    }
}
