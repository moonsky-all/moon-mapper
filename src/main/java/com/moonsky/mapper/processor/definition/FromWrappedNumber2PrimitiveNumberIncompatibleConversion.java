package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
public class FromWrappedNumber2PrimitiveNumberIncompatibleConversion extends BaseConversion implements Conversion {

    public FromWrappedNumber2PrimitiveNumberIncompatibleConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_Double, PRIMITIVE_float, this);
        registry.register(CLASS_Double, PRIMITIVE_long, this);
        registry.register(CLASS_Double, PRIMITIVE_int, this);
        registry.register(CLASS_Double, PRIMITIVE_short, this);
        registry.register(CLASS_Double, PRIMITIVE_byte, this);

        registry.register(CLASS_Float, PRIMITIVE_long, this);
        registry.register(CLASS_Float, PRIMITIVE_int, this);
        registry.register(CLASS_Float, PRIMITIVE_short, this);
        registry.register(CLASS_Float, PRIMITIVE_byte, this);

        registry.register(CLASS_Long, PRIMITIVE_int, this);
        registry.register(CLASS_Long, PRIMITIVE_short, this);
        registry.register(CLASS_Long, PRIMITIVE_byte, this);

        registry.register(CLASS_Integer, PRIMITIVE_short, this);
        registry.register(CLASS_Integer, PRIMITIVE_byte, this);

        registry.register(CLASS_Short, PRIMITIVE_byte, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        String setterPrimitiveType = findPrimitiveClass(setter.getPropertyActualType());
        scripts.onIfNotNull(var).scriptOf("{}.{}({}.{}Value())", THAT,//
            setter.getMethodName(), var, setterPrimitiveType);
    }
}
