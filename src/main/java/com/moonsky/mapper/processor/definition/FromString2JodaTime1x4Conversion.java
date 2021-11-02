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
public class FromString2JodaTime1x4Conversion extends BaseConversion implements Conversion {

    public FromString2JodaTime1x4Conversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (isNotImported1x4()) {
            return;
        }
        for (String classname : JODA_1x4_CLASSES) {
            registry.register(CLASS_String, classname, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String var = defineGetterValueVar(scripts, getter);
        onNull(scripts, setter, var).or("{}.parse{}({})",
            Imported.nameOf(setterActualType),
            Element2.getSimpleName(setterActualType),
            var);
    }
}
