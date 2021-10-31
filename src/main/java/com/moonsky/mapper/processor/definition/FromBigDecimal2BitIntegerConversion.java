package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public class FromBigDecimal2BitIntegerConversion extends BaseConversion {

    public FromBigDecimal2BitIntegerConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_BigDecimal, CLASS_BigInteger, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "{}.toBigInteger()", AS_ORIGINAL_ARGS);
    }
}
