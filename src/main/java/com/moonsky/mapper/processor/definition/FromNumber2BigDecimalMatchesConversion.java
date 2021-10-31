package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public class FromNumber2BigDecimalMatchesConversion extends BaseConversion {

    public FromNumber2BigDecimalMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_Number, CLASS_BigDecimal, this);
        registry.registerMatches(NUMBER_TESTER, CLASS_BigDecimal, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "{}.valueOf({}.doubleValue())", AS_BIG_DECIMAL__VAR);
    }
}
