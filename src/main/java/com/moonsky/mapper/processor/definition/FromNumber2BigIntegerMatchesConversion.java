package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public class FromNumber2BigIntegerMatchesConversion extends BaseConversion {

    public FromNumber2BigIntegerMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_Number, CLASS_BigInteger, this);
        registry.registerMatches(NUMBER_TESTER, CLASS_BigInteger, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "{}.valueOf({}.longValue())", AS_BIG_INTEGER__VAR);
    }
}
