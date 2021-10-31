package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.util.AliasConstant2;

/**
 * @author benshaoye
 */
public class FromJdkSqlDate2StringConversion extends BaseFromJdkDate2StringConversion {

    public FromJdkSqlDate2StringConversion() {super(CLASS_sql_Date, AliasConstant2.PATTERN_DATE);}
}
