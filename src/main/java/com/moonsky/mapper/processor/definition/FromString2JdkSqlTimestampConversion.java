package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.util.AliasConstant2;

/**
 * @author benshaoye
 */
public class FromString2JdkSqlTimestampConversion extends BaseFromString2JdkSqlDateConversion implements Conversion {

    public FromString2JdkSqlTimestampConversion() {super(CLASS_sql_Timestamp, AliasConstant2.PATTERN_DATETIME);}
}
