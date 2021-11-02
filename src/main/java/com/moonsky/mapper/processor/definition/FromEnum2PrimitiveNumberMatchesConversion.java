package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Test2;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;

/**
 * @author benshaoye
 */
public class FromEnum2PrimitiveNumberMatchesConversion extends BaseConversion implements Conversion {

    public FromEnum2PrimitiveNumberMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        for (String primitiveNumberType : PRIMITIVE_NUMBER_TYPES) {
            registry.registerMatches(Test2::isEnumClass, primitiveNumberType, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterPrimitiveType = setter.getPropertyActualType();
        String var = defineGetterValueVar(scripts, getter);
        if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, PRIMITIVE_int)) {
            scripts.onIfNotNull(var)
                .scriptOf("{}.{}(({}) {}.ordinal())", THAT, setter.getMethodName(), setterPrimitiveType, var);
        } else {
            scripts.onIfNotNull(var).scriptOf("{}.{}({}.ordinal())", THAT, setter.getMethodName(), var);
        }
    }
}
