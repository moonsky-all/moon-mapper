package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.util.AliasConstant2;
import com.moonsky.processor.processing.util.Collect2;
import com.moonsky.processor.processing.util.Import2;

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
        for (String classname : Collect2.list(AliasConstant2.Joda_LocalDate_ClassName,
            AliasConstant2.Joda_LocalTime_ClassName,
            AliasConstant2.Joda_LocalDateTime_ClassName)) {
            registry.register(CLASS_String, classname, this);
        }
    }
}
