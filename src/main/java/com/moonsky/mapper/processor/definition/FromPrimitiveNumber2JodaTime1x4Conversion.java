package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Element2;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.Test2;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.THIS;

/**
 * @author benshaoye
 */
public class FromPrimitiveNumber2JodaTime1x4Conversion extends BaseConversion implements Conversion {

    public FromPrimitiveNumber2JodaTime1x4Conversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (isNotImported1x4()) {
            return;
        }
        for (String classname : JODA_1x4_CLASSES) {
            for (String primitiveNumberType : PRIMITIVE_NUMBER_TYPES) {
                registry.register(primitiveNumberType, classname, this);
            }
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String getterActualType = setter.getPropertyActualType();
        String setterActualType = setter.getPropertyActualType();
        boolean isLtLong = Test2.isPrimitiveNumberSubtypeOf(getterActualType, PRIMITIVE_long);
        scripts.scriptOf(isLtLong ? "{}.{}({}.{}({}.{}()))" : "{}.{}({}.{}((int) {}.{}()))",

            THAT, setter.getMethodName(), Imported.nameOf(setterActualType),

            Element2.getSimpleName(setterActualType).toLowerCase(),

            THIS, getter.getMethodName());
    }
}
