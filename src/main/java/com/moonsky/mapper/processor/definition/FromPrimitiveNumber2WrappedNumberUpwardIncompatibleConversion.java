package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Imported;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.THIS;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
public class FromPrimitiveNumber2WrappedNumberUpwardIncompatibleConversion extends BaseConversion
    implements Conversion {

    public FromPrimitiveNumber2WrappedNumberUpwardIncompatibleConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(PRIMITIVE_byte, CLASS_Short, this);
        registry.register(PRIMITIVE_byte, CLASS_Integer, this);
        registry.register(PRIMITIVE_byte, CLASS_Long, this);
        registry.register(PRIMITIVE_byte, CLASS_Float, this);
        registry.register(PRIMITIVE_byte, CLASS_Double, this);

        registry.register(PRIMITIVE_short, CLASS_Integer, this);
        registry.register(PRIMITIVE_short, CLASS_Long, this);
        registry.register(PRIMITIVE_short, CLASS_Float, this);
        registry.register(PRIMITIVE_short, CLASS_Double, this);

        registry.register(PRIMITIVE_int, CLASS_Long, this);
        registry.register(PRIMITIVE_int, CLASS_Float, this);
        registry.register(PRIMITIVE_int, CLASS_Double, this);

        registry.register(PRIMITIVE_long, CLASS_Float, this);
        registry.register(PRIMITIVE_long, CLASS_Double, this);

        registry.register(PRIMITIVE_float, CLASS_Double, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        scripts.scriptOf("{}.{}({}.valueOf({}.{}()))", THAT, setter.getMethodName(),//
            Imported.nameOf(setter.getPropertyActualType()), THIS, getter.getMethodName());
    }
}
