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
@SuppressWarnings("all")
public class FromPrimitiveNumber2StringConversion extends BaseConversion implements Conversion {

    public FromPrimitiveNumber2StringConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(PRIMITIVE_double, CLASS_String, this);
        registry.register(PRIMITIVE_float, CLASS_String, this);
        registry.register(PRIMITIVE_long, CLASS_String, this);
        registry.register(PRIMITIVE_int, CLASS_String, this);
        registry.register(PRIMITIVE_short, CLASS_String, this);
        registry.register(PRIMITIVE_byte, CLASS_String, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        if (String2.isBlank(pattern)) {
            scripts.scriptOf("{}.{}({}.valueOf({}.{}()))", THAT, setter.getMethodName(),

                Imported.STRING, THIS, getter.getMethodName());
        } else {
            scripts.scriptOf("{}.{}({}.format({}.{}(), {}))", THAT,//
                setter.getMethodName(), FORMATTER_IMPORTED, THIS,//
                getter.getMethodName(), Stringify.of(pattern));
        }
    }
}
