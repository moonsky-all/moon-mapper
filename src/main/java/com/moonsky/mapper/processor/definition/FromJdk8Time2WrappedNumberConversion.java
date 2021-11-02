package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Imported;

import java.time.Month;
import java.time.Year;

import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;
import static com.moonsky.mapper.processor.definition.ConversionUtils.onNull;
import static com.moonsky.processor.processing.util.Test2.isSubtypeOfInt;

/**
 * @author benshaoye
 */
public class FromJdk8Time2WrappedNumberConversion extends BaseConversion implements Conversion {

    public FromJdk8Time2WrappedNumberConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        String yearClassname = classname(Year.class);
        String monthClassname = classname(Month.class);
        for (String wrappedNumberType : WRAPPED_NUMBER_TYPES) {
            if (!CLASS_Byte.equals(wrappedNumberType)) {
                registry.register(yearClassname, wrappedNumberType, this);
            }
            registry.register(monthClassname, wrappedNumberType, this);
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
            onNull(scripts, setter, var).or("{}.valueOf(({}) {}.getValue())",
                setterActualTypeImported,
                setterPrimitiveType,
                var);
        } else {
            onNull(scripts, setter, var).or("{}.valueOf({}.getValue())", setterActualTypeImported, var);
        }
    }
}
