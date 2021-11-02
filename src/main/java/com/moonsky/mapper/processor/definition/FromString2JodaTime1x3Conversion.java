package com.moonsky.mapper.processor.definition;

/**
 * @author benshaoye
 */
public class FromString2JodaTime1x3Conversion extends FromString2JodaTime1x0Conversion implements Conversion {

    public FromString2JodaTime1x3Conversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (isNotImported1x3()) {
            return;
        }
        for (String classname : JODA_1x3_CLASSES) {
            registry.register(CLASS_String, classname, this);
        }
    }
}
