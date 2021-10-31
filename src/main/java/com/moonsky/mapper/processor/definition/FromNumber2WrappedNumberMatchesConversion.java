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
        registry.register(CLASS_BigDecimal, CLASS_Double, this);
        registry.register(CLASS_BigDecimal, CLASS_Float, this);
        registry.register(CLASS_BigDecimal, CLASS_Long, this);
        registry.register(CLASS_BigDecimal, CLASS_Integer, this);
        registry.register(CLASS_BigDecimal, CLASS_Short, this);
        registry.register(CLASS_BigDecimal, CLASS_Byte, this);

        registry.register(CLASS_BigInteger, CLASS_Double, this);
        registry.register(CLASS_BigInteger, CLASS_Float, this);
        registry.register(CLASS_BigInteger, CLASS_Long, this);
        registry.register(CLASS_BigInteger, CLASS_Integer, this);
        registry.register(CLASS_BigInteger, CLASS_Short, this);
        registry.register(CLASS_BigInteger, CLASS_Byte, this);

        registry.register(CLASS_Number, CLASS_Double, this);
        registry.register(CLASS_Number, CLASS_Float, this);
        registry.register(CLASS_Number, CLASS_Long, this);
        registry.register(CLASS_Number, CLASS_Integer, this);
        registry.register(CLASS_Number, CLASS_Short, this);
        registry.register(CLASS_Number, CLASS_Byte, this);

        registry.registerMatches(NUMBER_TESTER, CLASS_Double, this);
        registry.registerMatches(NUMBER_TESTER, CLASS_Float, this);
        registry.registerMatches(NUMBER_TESTER, CLASS_Long, this);
        registry.registerMatches(NUMBER_TESTER, CLASS_Integer, this);
        registry.registerMatches(NUMBER_TESTER, CLASS_Short, this);
        registry.registerMatches(NUMBER_TESTER, CLASS_Byte, this);
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
