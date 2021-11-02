package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.String2;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
public class FromString2JodaTime2x0Conversion extends BaseConversion implements Conversion {

    public FromString2JodaTime2x0Conversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (isNotImported2x()) {
            return;
        }
        for (String klassName : JODA_1x0_CLASSES) {
            registry.register(CLASS_String, klassName, this);
        }
        for (String klassName : JODA_1x3_CLASSES) {
            registry.register(CLASS_String, klassName, this);
        }
        for (String klassName : JODA_2x0_CLASSES) {
            registry.register(CLASS_String, klassName, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        String setterActualType = setter.getPropertyActualType();
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        Imported<String> setterImported = Imported.nameOf(setterActualType);
        if (String2.isBlank(pattern)) {
            onNull(scripts, setter, var).or("{}.parse({})", setterImported, var);
        } else {
            String formatConst = defineJodaDateTimeFormatter(scripts, pattern);
            onNull(scripts, setter, var).or("{}.parse({}, {})", setterImported, var, formatConst);
        }
    }
}
