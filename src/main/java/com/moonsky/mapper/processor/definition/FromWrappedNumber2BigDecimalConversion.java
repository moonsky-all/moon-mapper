package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public class FromWrappedNumber2BigDecimalConversion extends BaseConversion {

    public FromWrappedNumber2BigDecimalConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        for (String wrappedNumberType : WRAPPED_NUMBER_TYPES) {
            registry.register(wrappedNumberType, CLASS_BigDecimal, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "{}.valueOf({})", AS_BIG_DECIMAL__VAR);
    }
}
