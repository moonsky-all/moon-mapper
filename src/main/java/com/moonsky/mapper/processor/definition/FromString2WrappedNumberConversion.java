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
public class FromString2WrappedNumberConversion extends BaseConversion implements Conversion {

    public FromString2WrappedNumberConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        for (String wrappedNumberType : WRAPPED_NUMBER_TYPES) {
            registry.register(CLASS_String, wrappedNumberType, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String setterPrimitiveType = findPrimitiveClass(setterActualType);
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        String var = defineGetterValueVar(scripts, getter);
        if (String2.isBlank(pattern)) {
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.valueOf({}))",

                THAT, setter.getMethodName(), Imported.nameOf(setterActualType), var);
        } else {
            scripts.onStringIfNotEmpty(var).scriptOf("{}.{}({}.parseNumber({}, {}).{}Value())",

                THAT, setter.getMethodName(), FORMATTER_IMPORTED, var, Stringify.of(pattern),

                setterPrimitiveType);
        }
        scripts.onElse().scriptOf("{}.{}(null)", THAT, setter.getMethodName());
    }
}
