package com.moonsky.mapper.processor.definition;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
public class FromNumber2PrimitiveNumberMatchesConversion extends FromWrappedNumber2PrimitiveNumberIncompatibleConversion
    implements Conversion {

    public FromNumber2PrimitiveNumberMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        /**
         * TODO: 2021/11/1 参考{@link FromNumber2WrappedNumberMatchesConversion}
         * 那里注册了 registry.register(CLASS_Number, wrappedNumberType, this);
         * 这里需要检查是否有遗漏
         */
        for (String primitiveNumberType : PRIMITIVE_NUMBER_TYPES) {
            registry.register(CLASS_BigDecimal, primitiveNumberType, this);
            registry.register(CLASS_BigInteger, primitiveNumberType, this);
            registry.registerMatches(NUMBER_TESTER, primitiveNumberType, this);
        }
    }
}
