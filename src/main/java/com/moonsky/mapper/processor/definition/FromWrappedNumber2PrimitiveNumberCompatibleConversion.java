package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;

/**
 * @author benshaoye
 */
public class FromWrappedNumber2PrimitiveNumberCompatibleConversion extends BaseConversion implements Conversion {

    public FromWrappedNumber2PrimitiveNumberCompatibleConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_Byte, PRIMITIVE_byte, this);
        registry.register(CLASS_Byte, PRIMITIVE_short, this);
        registry.register(CLASS_Byte, PRIMITIVE_int, this);
        registry.register(CLASS_Byte, PRIMITIVE_long, this);
        registry.register(CLASS_Byte, PRIMITIVE_float, this);
        registry.register(CLASS_Byte, PRIMITIVE_double, this);

        registry.register(CLASS_Short, PRIMITIVE_short, this);
        registry.register(CLASS_Short, PRIMITIVE_int, this);
        registry.register(CLASS_Short, PRIMITIVE_long, this);
        registry.register(CLASS_Short, PRIMITIVE_float, this);
        registry.register(CLASS_Short, PRIMITIVE_double, this);

        registry.register(CLASS_Integer, PRIMITIVE_int, this);
        registry.register(CLASS_Integer, PRIMITIVE_long, this);
        registry.register(CLASS_Integer, PRIMITIVE_float, this);
        registry.register(CLASS_Integer, PRIMITIVE_double, this);

        registry.register(CLASS_Long, PRIMITIVE_long, this);
        registry.register(CLASS_Long, PRIMITIVE_float, this);
        registry.register(CLASS_Long, PRIMITIVE_double, this);

        registry.register(CLASS_Float, PRIMITIVE_float, this);
        registry.register(CLASS_Float, PRIMITIVE_double, this);

        registry.register(CLASS_Double, PRIMITIVE_double, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        scripts.onIfNotNull(var).scriptOf("{}.{}({})", THAT, setter.getMethodName(), var);
    }
}
