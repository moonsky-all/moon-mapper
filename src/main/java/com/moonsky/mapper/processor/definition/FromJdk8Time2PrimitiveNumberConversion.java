package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Test2;

import java.time.Month;
import java.time.Year;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;

/**
 * @author benshaoye
 */
public class FromJdk8Time2PrimitiveNumberConversion extends BaseConversion implements Conversion {

    public FromJdk8Time2PrimitiveNumberConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        String yearClassname = classname(Year.class);
        String monthClassname = classname(Month.class);
        for (String primitiveNumberType : PRIMITIVE_NUMBER_TYPES) {
            if (!PRIMITIVE_byte.equals(primitiveNumberType)) {
                registry.register(yearClassname, primitiveNumberType, this);
            }
            registry.register(monthClassname, primitiveNumberType, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String setterPrimitiveType = findPrimitiveClass(setterActualType);
        String var = defineGetterValueVar(scripts, getter);
        if (Test2.isPrimitiveNumberSubtypeOf(setterPrimitiveType, PRIMITIVE_int)) {
            scripts.onIfNotNull(var).scriptOf("{}.{}(({}) {}.getValue())",

                THAT, setter.getMethodName(), setterPrimitiveType, var);
        } else {
            scripts.onIfNotNull(var).scriptOf("{}.{}({}.getValue())",

                THAT, setter.getMethodName(), var);
        }
    }
}
