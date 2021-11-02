package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Element2;
import com.moonsky.processor.processing.util.Test2;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;

/**
 * @author benshaoye
 */
public class FromJoda1x4Time2PrimitiveNumberConversion extends BaseConversion implements Conversion {

    public FromJoda1x4Time2PrimitiveNumberConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (isNotImported1x4()) {
            return;
        }
        for (String joda1x4Class : JODA_1x4_CLASSES) {
            for (String primitiveNumberClass : PRIMITIVE_NUMBER_TYPES) {
                registry.register(joda1x4Class, primitiveNumberClass, this);
            }
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterPrimitiveType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();
        String var = defineGetterValueVar(scripts, getter);
        if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, PRIMITIVE_int)) {
            scripts.onIfNotNull(var).scriptOf("{}.{}(({}) {}.get{}())", THAT,

                setter.getMethodName(), setterPrimitiveType, var, Element2.getSimpleName(getterActualType));
        } else {
            scripts.onIfNotNull(var).scriptOf("{}.{}({}.get{}())", THAT,

                setter.getMethodName(), var, Element2.getSimpleName(getterActualType));
        }
    }
}
