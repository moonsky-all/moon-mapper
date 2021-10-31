package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
public class WrappedNumberIncompatibleConversion extends BaseConversion implements Conversion {

    public WrappedNumberIncompatibleConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_Double, CLASS_Float, this);
        registry.register(CLASS_Double, CLASS_Long, this);
        registry.register(CLASS_Double, CLASS_Integer, this);
        registry.register(CLASS_Double, CLASS_Short, this);
        registry.register(CLASS_Double, CLASS_Byte, this);

        registry.register(CLASS_Float, CLASS_Long, this);
        registry.register(CLASS_Float, CLASS_Integer, this);
        registry.register(CLASS_Float, CLASS_Short, this);
        registry.register(CLASS_Float, CLASS_Byte, this);

        registry.register(CLASS_Long, CLASS_Integer, this);
        registry.register(CLASS_Long, CLASS_Short, this);
        registry.register(CLASS_Long, CLASS_Byte, this);

        registry.register(CLASS_Integer, CLASS_Short, this);
        registry.register(CLASS_Integer, CLASS_Byte, this);

        registry.register(CLASS_Short, CLASS_Byte, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "{}.{}Value()", var -> {
            return objectsOf(var, findPrimitiveClass(setter.getPropertyActualType()));
        });
    }
}
