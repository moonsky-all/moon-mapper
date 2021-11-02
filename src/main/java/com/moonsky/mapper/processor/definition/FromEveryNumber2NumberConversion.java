package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public class FromEveryNumber2NumberConversion extends BaseConversion implements Conversion {

    public FromEveryNumber2NumberConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        for (String primitiveNumberType : PRIMITIVE_NUMBER_TYPES) {
            registry.register(primitiveNumberType, CLASS_Number, this);
        }

        for (String wrappedNumberType : WRAPPED_NUMBER_TYPES) {
            registry.register(wrappedNumberType, CLASS_Number, this);
        }

        registry.register(CLASS_BigDecimal, CLASS_Number, this);
        registry.register(CLASS_BigInteger, CLASS_Number, this);

        registry.registerMatches(NUMBER_TESTER, CLASS_Number, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {

    }
}
