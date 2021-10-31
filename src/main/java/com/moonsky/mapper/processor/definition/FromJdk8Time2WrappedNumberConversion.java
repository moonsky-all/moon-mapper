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
        registry.register(classname(Year.class), CLASS_Double, this);
        registry.register(classname(Year.class), CLASS_Float, this);
        registry.register(classname(Year.class), CLASS_Long, this);
        registry.register(classname(Year.class), CLASS_Integer, this);
        registry.register(classname(Year.class), CLASS_Short, this);
        registry.register(classname(Year.class), CLASS_Byte, this);

        registry.register(classname(Month.class), CLASS_Double, this);
        registry.register(classname(Month.class), CLASS_Float, this);
        registry.register(classname(Month.class), CLASS_Long, this);
        registry.register(classname(Month.class), CLASS_Integer, this);
        registry.register(classname(Month.class), CLASS_Short, this);
        registry.register(classname(Month.class), CLASS_Byte, this);
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
