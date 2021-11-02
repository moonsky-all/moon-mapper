package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;
import static com.moonsky.mapper.processor.definition.ConversionUtils.onNull;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
public class FromNumber2WrappedNumberMatchesConversion extends BaseConversion implements Conversion {

    public FromNumber2WrappedNumberMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        for (String wrappedNumberType : WRAPPED_NUMBER_TYPES) {
            registry.register(CLASS_BigDecimal, wrappedNumberType, this);
            registry.register(CLASS_BigInteger, wrappedNumberType, this);
            registry.register(CLASS_Number, wrappedNumberType, this);
            registry.registerMatches(NUMBER_TESTER, wrappedNumberType, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        String setterPrimitiveType = findPrimitiveClass(setter.getPropertyActualType());
        onNull(scripts, setter, var).or("{}.{}Value()", var, setterPrimitiveType);
    }
}
