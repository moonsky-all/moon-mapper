package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.*;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
public class FromString2JodaTime2x0Conversion extends BaseConversion implements Conversion {

    public FromString2JodaTime2x0Conversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (!Import2.JODA_TIME_2X) {
            return;
        }
        for (String classname : Collect2.list(AliasConstant2.Joda_DateTime_ClassName,
            AliasConstant2.Joda_Instant_ClassName,
            AliasConstant2.Joda_LocalDate_ClassName,
            AliasConstant2.Joda_LocalTime_ClassName,
            AliasConstant2.Joda_LocalDateTime_ClassName,
            AliasConstant2.Joda_MutableDateTime_ClassName,
            AliasConstant2.Joda_YearMonth_ClassName,
            AliasConstant2.Joda_MonthDay_ClassName)) {
            registry.register(CLASS_String, classname, this);
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
