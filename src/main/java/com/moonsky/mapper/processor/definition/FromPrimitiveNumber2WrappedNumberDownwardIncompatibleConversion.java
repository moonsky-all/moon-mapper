package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.THIS;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
public class FromPrimitiveNumber2WrappedNumberDownwardIncompatibleConversion extends BaseConversion
    implements Conversion {

    public FromPrimitiveNumber2WrappedNumberDownwardIncompatibleConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(PRIMITIVE_double, CLASS_Float, this);
        registry.register(PRIMITIVE_double, CLASS_Long, this);
        registry.register(PRIMITIVE_double, CLASS_Integer, this);
        registry.register(PRIMITIVE_double, CLASS_Short, this);
        registry.register(PRIMITIVE_double, CLASS_Byte, this);

        registry.register(PRIMITIVE_float, CLASS_Long, this);
        registry.register(PRIMITIVE_float, CLASS_Integer, this);
        registry.register(PRIMITIVE_float, CLASS_Short, this);
        registry.register(PRIMITIVE_float, CLASS_Byte, this);

        registry.register(PRIMITIVE_long, CLASS_Integer, this);
        registry.register(PRIMITIVE_long, CLASS_Short, this);
        registry.register(PRIMITIVE_long, CLASS_Byte, this);

        registry.register(PRIMITIVE_int, CLASS_Short, this);
        registry.register(PRIMITIVE_int, CLASS_Byte, this);

        registry.register(PRIMITIVE_short, CLASS_Byte, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterPrimitiveActualType = findPrimitiveClass(setter.getPropertyActualType());
        scripts.scriptOf("{}.{}(({}) {}.{}())", THAT, setter.getMethodName(),//
            setterPrimitiveActualType, THIS, getter.getMethodName());
    }
}
