package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public class FromBitInteger2BigDecimalConversion extends BaseConversion {

    public FromBitInteger2BigDecimalConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_BigInteger, CLASS_BigDecimal, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "new {}({})", AS_BIG_DECIMAL__VAR);
    }
}
