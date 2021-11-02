package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import static com.moonsky.mapper.processor.definition.ConversionUtils.doGetterVal;
import static com.moonsky.mapper.processor.definition.ConversionUtils.setValueOf;

/**
 * @author benshaoye
 */
public class FromPrimitiveNumber2BigDecimalConversion extends BaseConversion implements Conversion {

    public FromPrimitiveNumber2BigDecimalConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        for (String primitiveNumberType : PRIMITIVE_NUMBER_TYPES) {
            registry.register(primitiveNumberType, CLASS_BigDecimal, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {setValueOf(scripts, setter, doGetterVal(getter));}
}
