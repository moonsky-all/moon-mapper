package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.String2;
import com.moonsky.processor.processing.util.Stringify;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
public class FromString2BigDecimalConversion extends BaseConversion implements Conversion {

    public FromString2BigDecimalConversion() {
    }

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_String, CLASS_BigDecimal, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        if (String2.isBlank(pattern)) {
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}(new {}({}))",

                THAT, setter.getMethodName(), Imported.BIG_DECIMAL, var);
        } else {
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parseBigDecimal({}, {}))", THAT,

                setter.getMethodName(), FORMATTER_IMPORTED, var, Stringify.of(pattern));
        }
        scripts.onElseNull(THAT, setter.getMethodName());
    }
}
