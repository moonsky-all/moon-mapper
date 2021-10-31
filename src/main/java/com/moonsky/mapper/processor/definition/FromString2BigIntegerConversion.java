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
public class FromString2BigIntegerConversion extends BaseConversion implements Conversion {

    public FromString2BigIntegerConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_String, CLASS_BigInteger, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        final String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        String var = defineGetterValueVar(scripts, getter);
        if (String2.isBlank(pattern)) {
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}(new {}({}))",

                THAT, setter.getMethodName(), Imported.BIG_INTEGER, var);
        } else {
            String template = "{}.{}({}.valueOf({}.parseNumber({}, {}).longValue()))";
            scripts.onStringIfNotEmpty(var).scriptOf(template, THAT, setter.getMethodName(),

                Imported.BIG_INTEGER, FORMATTER_IMPORTED, var, Stringify.of(pattern));
        }
        scripts.onElse().scriptOf("{}.{}(null)", THAT, setter.getMethodName());
    }
}
