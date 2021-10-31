package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.util.AliasConstant2;

/**
 * @author benshaoye
 */
public class FromJdkSqlTime2StringConversion extends BaseFromJdkDate2StringConversion {

    public FromJdkSqlTime2StringConversion() {super(CLASS_sql_Time, AliasConstant2.PATTERN_TIME);}
}
