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
public class FromCharSequence2Joda1x4TimeMatchesConversion extends BaseConversion implements Conversion {

    public FromCharSequence2Joda1x4TimeMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (isNotImported1x4()) {
            return;
        }
        String classnameOfStringBuffer = classname(StringBuffer.class);
        String classnameOfStringBuilder = classname(StringBuilder.class);
        for (String classname : JODA_1x4_CLASSES) {
            registry.register(CLASS_CharSequence, classname, this);
            registry.register(classnameOfStringBuffer, classname, this);
            registry.register(classnameOfStringBuilder, classname, this);
            registry.registerMatches(testerSubtypeOf(CLASS_CharSequence), classname, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String var = defineGetterValueVar(scripts, getter);
        onNull(scripts, setter, var).or("{}.parse{}({}.toString())",
            Imported.nameOf(setterActualType),
            Element2.getSimpleName(setterActualType),
            var);
    }
}
