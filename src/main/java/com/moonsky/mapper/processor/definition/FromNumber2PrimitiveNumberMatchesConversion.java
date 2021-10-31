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
        registry.register(CLASS_BigDecimal, PRIMITIVE_double, this);
        registry.register(CLASS_BigDecimal, PRIMITIVE_float, this);
        registry.register(CLASS_BigDecimal, PRIMITIVE_long, this);
        registry.register(CLASS_BigDecimal, PRIMITIVE_int, this);
        registry.register(CLASS_BigDecimal, PRIMITIVE_short, this);
        registry.register(CLASS_BigDecimal, PRIMITIVE_byte, this);

        registry.register(CLASS_BigInteger, PRIMITIVE_double, this);
        registry.register(CLASS_BigInteger, PRIMITIVE_float, this);
        registry.register(CLASS_BigInteger, PRIMITIVE_long, this);
        registry.register(CLASS_BigInteger, PRIMITIVE_int, this);
        registry.register(CLASS_BigInteger, PRIMITIVE_short, this);
        registry.register(CLASS_BigInteger, PRIMITIVE_byte, this);

        registry.registerMatches(NUMBER_TESTER, PRIMITIVE_double, this);
        registry.registerMatches(NUMBER_TESTER, PRIMITIVE_float, this);
        registry.registerMatches(NUMBER_TESTER, PRIMITIVE_long, this);
        registry.registerMatches(NUMBER_TESTER, PRIMITIVE_int, this);
        registry.registerMatches(NUMBER_TESTER, PRIMITIVE_short, this);
        registry.registerMatches(NUMBER_TESTER, PRIMITIVE_byte, this);
    }
}
