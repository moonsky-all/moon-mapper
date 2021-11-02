package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Element2;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.Log2;

import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;
import static com.moonsky.mapper.processor.definition.ConversionUtils.onNull;
import static com.moonsky.processor.processing.util.Test2.isSubtypeOfInt;

/**
 * @author benshaoye
 */
public class FromJoda1x4Time2WrappedNumberConversion extends BaseConversion implements Conversion {

    public FromJoda1x4Time2WrappedNumberConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (isNotImported1x4()) {
            return;
        }
        String[] wrappedNumberClasses = {
            CLASS_Double, CLASS_Float, CLASS_Long, CLASS_Integer, CLASS_Short, CLASS_Byte,
        };
        for (String joda1x4Class : JODA_1x4_CLASSES) {
            for (String wrappedNumberClass : wrappedNumberClasses) {
                registry.register(joda1x4Class, wrappedNumberClass, this);
            }
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String setterPrimitiveType = findPrimitiveClass(setterActualType);
        Imported<?> setterActualImported = Imported.nameOf(setterActualType);
        String getterSimpleName = Element2.getSimpleName(getter.getPropertyActualType());
        String var = defineGetterValueVar(scripts, getter);
        try {
            if (isSubtypeOfInt(setterPrimitiveType)) {
                onNull(scripts, setter, var).or("{}.valueOf(({}) {}.get{}())",
                    setterActualImported,
                    setterPrimitiveType,
                    var,
                    getterSimpleName);
            } else {
                onNull(scripts, setter, var).or("{}.valueOf({}.get{}())", setterActualImported, var, getterSimpleName);
            }
        } catch (RuntimeException e) {
            Log2.warn("Wrapped: {}, primitive: {}", setterActualType, setterPrimitiveType);
            throw e;
        }
    }
}
