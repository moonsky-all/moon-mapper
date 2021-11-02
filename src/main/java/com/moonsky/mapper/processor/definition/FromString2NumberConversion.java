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
public class FromString2NumberConversion extends BaseConversion implements Conversion {

    public FromString2NumberConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_String, CLASS_Number, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        String var = defineGetterValueVar(scripts, getter);
        if (String2.isBlank(pattern)) {
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.valueOf({}))",

                THAT, setter.getMethodName(), Imported.of(Double.class), var);
        } else {
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parseNumber({}, {}))",

                THAT, setter.getMethodName(), FORMATTER_IMPORTED, var, Stringify.of(pattern));
        }
        scripts.onElse().scriptOf("{}.{}(null)", THAT, setter.getMethodName());
    }
}
