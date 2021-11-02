package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.Test2;

import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;
import static com.moonsky.mapper.processor.definition.ConversionUtils.onNull;
import static com.moonsky.processor.processing.util.Test2.isSubtypeOfInt;

/**
 * @author benshaoye
 */
public class FromEnum2WrappedNumberMatchesConversion extends BaseConversion implements Conversion {

    public FromEnum2WrappedNumberMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        for (String wrappedNumberType : WRAPPED_NUMBER_TYPES) {
            registry.registerMatches(Test2::isEnumClass, wrappedNumberType, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String setterPrimitiveType = findPrimitiveClass(setterActualType);
        Imported<?> setterActualTypeImported = Imported.nameOf(setterActualType);
        String var = defineGetterValueVar(scripts, getter);
        if (isSubtypeOfInt(setterPrimitiveType)) {
            onNull(scripts, setter, var).or("{}.valueOf(({}) {}.ordinal())",
                setterActualTypeImported,
                setterPrimitiveType,
                var);
        } else {
            onNull(scripts, setter, var).or("{}.valueOf({}.ordinal())", setterActualTypeImported, var);
        }
    }
}
