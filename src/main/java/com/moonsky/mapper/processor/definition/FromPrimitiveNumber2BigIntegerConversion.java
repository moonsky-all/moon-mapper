package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Test2;

import static com.moonsky.mapper.processor.definition.ConversionUtils.doGetterVal;
import static com.moonsky.mapper.processor.definition.ConversionUtils.setValueOf;

/**
 * @author benshaoye
 */
public class FromPrimitiveNumber2BigIntegerConversion extends BaseConversion implements Conversion {

    public FromPrimitiveNumber2BigIntegerConversion() {
    }

    @Override
    public void register(ConversionRegistry registry) {
        for (String primitiveNumberType : PRIMITIVE_NUMBER_TYPES) {
            registry.register(primitiveNumberType, CLASS_BigInteger, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String cast = Test2.isLeLongClass(getter.getPropertyActualType()) ? null : "(long)";
        setValueOf(scripts, setter, doGetterVal(getter, cast));
    }
}
