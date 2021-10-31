package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.util.AliasConstant2;

/**
 * @author benshaoye
 */
public class FromString2JdkSqlDateConversion extends BaseFromString2JdkSqlDateConversion implements Conversion {

    public FromString2JdkSqlDateConversion() {super(CLASS_sql_Date, AliasConstant2.PATTERN_DATE);}
}
