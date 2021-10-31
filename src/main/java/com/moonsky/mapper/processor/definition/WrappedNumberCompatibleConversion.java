package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;
import static com.moonsky.mapper.processor.definition.ConversionUtils.onNull;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
public class WrappedNumberCompatibleConversion extends BaseConversion implements Conversion {

    public WrappedNumberCompatibleConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_Float, CLASS_Double, this);
        registry.register(CLASS_Long, CLASS_Double, this);
        registry.register(CLASS_Integer, CLASS_Double, this);
        registry.register(CLASS_Short, CLASS_Double, this);
        registry.register(CLASS_Byte, CLASS_Double, this);

        registry.register(CLASS_Long, CLASS_Float, this);
        registry.register(CLASS_Integer, CLASS_Float, this);
        registry.register(CLASS_Short, CLASS_Float, this);
        registry.register(CLASS_Byte, CLASS_Float, this);

        registry.register(CLASS_Integer, CLASS_Long, this);
        registry.register(CLASS_Short, CLASS_Long, this);
        registry.register(CLASS_Byte, CLASS_Long, this);

        registry.register(CLASS_Short, CLASS_Integer, this);
        registry.register(CLASS_Byte, CLASS_Integer, this);

        registry.register(CLASS_Byte, CLASS_Short, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        onNull(scripts, setter, var).or("({}) {}", findPrimitiveClass(setter.getPropertyActualType()), var);
    }
}
