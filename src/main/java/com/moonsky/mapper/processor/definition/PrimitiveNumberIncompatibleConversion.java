package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.THIS;

/**
 * @author benshaoye
 */
public class PrimitiveNumberIncompatibleConversion extends BaseConversion implements Conversion {

    public PrimitiveNumberIncompatibleConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(PRIMITIVE_double, PRIMITIVE_byte, this);
        registry.register(PRIMITIVE_double, PRIMITIVE_short, this);
        registry.register(PRIMITIVE_double, PRIMITIVE_int, this);
        registry.register(PRIMITIVE_double, PRIMITIVE_long, this);
        registry.register(PRIMITIVE_double, PRIMITIVE_float, this);

        registry.register(PRIMITIVE_float, PRIMITIVE_byte, this);
        registry.register(PRIMITIVE_float, PRIMITIVE_short, this);
        registry.register(PRIMITIVE_float, PRIMITIVE_int, this);
        registry.register(PRIMITIVE_float, PRIMITIVE_long, this);

        registry.register(PRIMITIVE_long, PRIMITIVE_byte, this);
        registry.register(PRIMITIVE_long, PRIMITIVE_short, this);
        registry.register(PRIMITIVE_long, PRIMITIVE_int, this);

        registry.register(PRIMITIVE_int, PRIMITIVE_byte, this);
        registry.register(PRIMITIVE_int, PRIMITIVE_short, this);

        registry.register(PRIMITIVE_short, PRIMITIVE_byte, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        scripts.scriptOf("{}.{}(({}) {}.{}())", THAT, setter.getMethodName(),

            setter.getPropertyActualType(), THIS, getter.getMethodName());
    }
}
