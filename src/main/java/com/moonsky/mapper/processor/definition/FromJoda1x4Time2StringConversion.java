package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.AliasConstant2;

/**
 * @author benshaoye
 */
public class FromJoda1x4Time2StringConversion extends BaseConversion implements Conversion {

    public FromJoda1x4Time2StringConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(AliasConstant2.Joda_Years_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_Months_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_Weeks_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_Days_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_Hours_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_Minutes_ClassName, CLASS_String, this);
        registry.register(AliasConstant2.Joda_Seconds_ClassName, CLASS_String, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "{}.toString()", AS_ORIGINAL_ARGS);
    }
}
