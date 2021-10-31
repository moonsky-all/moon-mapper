package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.util.AliasConstant2;

/**
 * supports: util.Date、util.Calendar、sql.Timestamp
 *
 * @author benshaoye
 */
public class FromJdkUtilDate2StringConversion extends BaseFromJdkDate2StringConversion {

    public FromJdkUtilDate2StringConversion() {super(null, AliasConstant2.PATTERN_DATETIME);}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_util_Date, CLASS_String, this);
        registry.register(CLASS_sql_Timestamp, CLASS_String, this);
        registry.register(CLASS_util_Calendar, CLASS_String, this);

        registry.registerMatches(testerSubtypeOf(CLASS_util_Date), CLASS_String, this);
        registry.registerMatches(testerSubtypeOf(CLASS_util_Calendar), CLASS_String, this);
    }
}
