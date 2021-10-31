package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public class FromEveryNumber2NumberConversion extends BaseConversion implements Conversion{

    public FromEveryNumber2NumberConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(PRIMITIVE_byte, CLASS_Number, this);
        registry.register(PRIMITIVE_short, CLASS_Number, this);
        registry.register(PRIMITIVE_int, CLASS_Number, this);
        registry.register(PRIMITIVE_long, CLASS_Number, this);
        registry.register(PRIMITIVE_float, CLASS_Number, this);
        registry.register(PRIMITIVE_double, CLASS_Number, this);

        registry.register(CLASS_Byte, CLASS_Number, this);
        registry.register(CLASS_Short, CLASS_Number, this);
        registry.register(CLASS_Integer, CLASS_Number, this);
        registry.register(CLASS_Long, CLASS_Number, this);
        registry.register(CLASS_Float, CLASS_Number, this);
        registry.register(CLASS_Double, CLASS_Number, this);

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
