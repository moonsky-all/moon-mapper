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
public class FromString2PrimitiveNumberConversion extends BaseConversion implements Conversion {

    public FromString2PrimitiveNumberConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_String, PRIMITIVE_double, this);
        registry.register(CLASS_String, PRIMITIVE_float, this);
        registry.register(CLASS_String, PRIMITIVE_long, this);
        registry.register(CLASS_String, PRIMITIVE_int, this);
        registry.register(CLASS_String, PRIMITIVE_short, this);
        registry.register(CLASS_String, PRIMITIVE_byte, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterPrimitiveType = setter.getPropertyActualType();
        String setterWrappedClass = findWrappedClass(setterPrimitiveType);
        String capitalizedSetterPrimitiveType = String2.capitalize(setterPrimitiveType);
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        String var = defineGetterValueVar(scripts, getter);
        if (String2.isBlank(pattern)) {
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parse{}({}))",

                THAT, setter.getMethodName(), Imported.nameOf(setterWrappedClass),

                capitalizedSetterPrimitiveType, var);
        } else {
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parseNumber({}, {}).{}Value())",

                THAT, setter.getMethodName(), FORMATTER_IMPORTED,

                var, Stringify.of(pattern), setterPrimitiveType);
        }
    }
}
