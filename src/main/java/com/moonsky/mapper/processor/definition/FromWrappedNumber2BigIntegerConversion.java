package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public class FromWrappedNumber2BigIntegerConversion extends BaseConversion {

    public FromWrappedNumber2BigIntegerConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_Double, CLASS_BigInteger, this);
        registry.register(CLASS_Float, CLASS_BigInteger, this);
        registry.register(CLASS_Long, CLASS_BigInteger, this);
        registry.register(CLASS_Integer, CLASS_BigInteger, this);
        registry.register(CLASS_Short, CLASS_BigInteger, this);
        registry.register(CLASS_Byte, CLASS_BigInteger, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "{}.valueOf({}.longValue())", AS_BIG_INTEGER__VAR);
    }
}
