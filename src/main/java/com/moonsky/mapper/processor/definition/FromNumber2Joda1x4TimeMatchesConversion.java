package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Element2;
import com.moonsky.processor.processing.util.Imported;

import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;
import static com.moonsky.mapper.processor.definition.ConversionUtils.onNull;

/**
 * @author benshaoye
 */
public class FromNumber2Joda1x4TimeMatchesConversion extends BaseConversion implements Conversion {

    public FromNumber2Joda1x4TimeMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (isNotImported1x4()) {
            return;
        }
        for (String classname : JODA_1x4_CLASSES) {
            for (String wrappedNumberType : WRAPPED_NUMBER_TYPES) {
                registry.register(wrappedNumberType, classname, this);
            }
            registry.register(CLASS_Number, classname, this);
            registry.registerMatches(NUMBER_TESTER, classname, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String var = defineGetterValueVar(scripts, getter);
        onNull(scripts, setter, var).or("{}.{}({}.intValue())",
            Imported.nameOf(setterActualType),
            Element2.getSimpleName(setterActualType).toLowerCase(),
            var);
    }
}
