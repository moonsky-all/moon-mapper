package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.*;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;
import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.processor.processing.util.Test2.isSubtypeOfInt;

/**
 * @author benshaoye
 */
public class FromJoda1x4Time2PrimitiveNumberConversion extends BaseConversion implements Conversion {

    public FromJoda1x4Time2PrimitiveNumberConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (!Import2.JODA_TIME_1X4) {
            return;
        }
        String[] joda1x4Classes = {
            AliasConstant2.Joda_Years_ClassName,
            AliasConstant2.Joda_Months_ClassName,
            AliasConstant2.Joda_Weeks_ClassName,
            AliasConstant2.Joda_Days_ClassName,
            AliasConstant2.Joda_Hours_ClassName,
            AliasConstant2.Joda_Minutes_ClassName,
            AliasConstant2.Joda_Seconds_ClassName
        };
        String[] wrappedNumberClasses = {
            PRIMITIVE_double, PRIMITIVE_float, PRIMITIVE_long, PRIMITIVE_int, PRIMITIVE_short, PRIMITIVE_byte,
        };
        for (String joda1x4Class : joda1x4Classes) {
            for (String wrappedNumberClass : wrappedNumberClasses) {
                registry.register(joda1x4Class, wrappedNumberClass, this);
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
