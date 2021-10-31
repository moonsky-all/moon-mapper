package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.Test2;

import java.time.Month;
import java.time.Year;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;
import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.processor.processing.util.Test2.isSubtypeOfInt;

/**
 * @author benshaoye
 */
public class FromJdk8Time2PrimitiveNumberConversion extends BaseConversion implements Conversion {

    public FromJdk8Time2PrimitiveNumberConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(classname(Year.class), PRIMITIVE_double, this);
        registry.register(classname(Year.class), PRIMITIVE_float, this);
        registry.register(classname(Year.class), PRIMITIVE_long, this);
        registry.register(classname(Year.class), PRIMITIVE_int, this);
        registry.register(classname(Year.class), PRIMITIVE_short, this);
        registry.register(classname(Year.class), PRIMITIVE_byte, this);

        registry.register(classname(Month.class), PRIMITIVE_double, this);
        registry.register(classname(Month.class), PRIMITIVE_float, this);
        registry.register(classname(Month.class), PRIMITIVE_long, this);
        registry.register(classname(Month.class), PRIMITIVE_int, this);
        registry.register(classname(Month.class), PRIMITIVE_short, this);
        registry.register(classname(Month.class), PRIMITIVE_byte, this);
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
