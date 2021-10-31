package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.AliasConstant2;
import com.moonsky.processor.processing.util.String2;
import com.moonsky.processor.processing.util.Test2;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;
import static com.moonsky.mapper.processor.definition.ConversionUtils.onNull;

/**
 * @author benshaoye
 */
public class FromJoda2x0TimeStringConversion extends BaseConversion implements Conversion {

    public FromJoda2x0TimeStringConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(AliasConstant2.Joda_DateTime_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_MutableDateTime_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_LocalDate_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_LocalTime_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_LocalDateTime_ClassName, CLASS_String, this);

        registry.register(AliasConstant2.Joda_DateTime_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_Instant_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_LocalDate_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_LocalTime_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_LocalDateTime_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_MutableDateTime_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_YearMonth_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_MonthDay_ClassName, CLASS_String, this);

        registry.registerMatches(Test2::isImportedAndJodaDateClass, CLASS_String, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        if (String2.isBlank(pattern)) {
            String formatConst = defineJodaDateTimeFormatter(scripts, pattern);
            String var = defineGetterValueVar(scripts, getter);
            onNull(scripts, setter, var).or("{}.print({})", formatConst, var);
        } else {
            onNullOr(scripts, getter, setter, "{}.toString()", AS_ORIGINAL_ARGS);
        }
    }
}
