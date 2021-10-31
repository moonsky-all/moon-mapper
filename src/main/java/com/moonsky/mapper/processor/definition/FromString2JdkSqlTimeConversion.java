package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.util.AliasConstant2;

/**
 * @author benshaoye
 */
public class FromString2JdkSqlTimeConversion extends BaseFromString2JdkSqlDateConversion implements Conversion {

    public FromString2JdkSqlTimeConversion() {super(CLASS_sql_Time, AliasConstant2.PATTERN_TIME);}
}
