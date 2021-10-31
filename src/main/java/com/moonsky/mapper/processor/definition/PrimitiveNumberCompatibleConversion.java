package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.THIS;

/**
 * @author benshaoye
 */
public class PrimitiveNumberCompatibleConversion extends BaseConversion implements Conversion {

    public PrimitiveNumberCompatibleConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(PRIMITIVE_byte, PRIMITIVE_short, this);
        registry.register(PRIMITIVE_byte, PRIMITIVE_int, this);
        registry.register(PRIMITIVE_byte, PRIMITIVE_long, this);
        registry.register(PRIMITIVE_byte, PRIMITIVE_float, this);
        registry.register(PRIMITIVE_byte, PRIMITIVE_double, this);

        registry.register(PRIMITIVE_short, PRIMITIVE_int, this);
        registry.register(PRIMITIVE_short, PRIMITIVE_long, this);
        registry.register(PRIMITIVE_short, PRIMITIVE_float, this);
        registry.register(PRIMITIVE_short, PRIMITIVE_double, this);

        registry.register(PRIMITIVE_int, PRIMITIVE_long, this);
        registry.register(PRIMITIVE_int, PRIMITIVE_float, this);
        registry.register(PRIMITIVE_int, PRIMITIVE_double, this);

        registry.register(PRIMITIVE_long, PRIMITIVE_float, this);
        registry.register(PRIMITIVE_long, PRIMITIVE_double, this);

        registry.register(PRIMITIVE_float, PRIMITIVE_double, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
    }
}
