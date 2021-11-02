package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.String2;
import com.moonsky.processor.processing.util.Stringify;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
public class FromNumber2StringMatchesConversion extends BaseConversion implements Conversion {

    public FromNumber2StringMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        for (String wrappedNumberType : WRAPPED_NUMBER_TYPES) {
            registry.register(wrappedNumberType, CLASS_String, this);
        }

        registry.register(CLASS_BigInteger, CLASS_String, this);
        registry.register(CLASS_BigDecimal, CLASS_String, this);
        registry.register(CLASS_Number, CLASS_String, this);

        registry.registerMatches(NUMBER_TESTER, CLASS_String, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        if (String2.isBlank(pattern)) {
            onNullOr(scripts, getter, setter, "{}.toString()", AS_ORIGINAL_ARGS);
        } else {
            String var = defineGetterValueVar(scripts, getter);
            onNull(scripts, setter, var).or("{}.format({}, {})", FORMATTER_IMPORTED, var, Stringify.of(pattern));
        }
    }
}
