package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.*;

import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;
import static com.moonsky.mapper.processor.definition.ConversionUtils.onNull;

/**
 * @author benshaoye
 */
public class FromString2JodaTime1x4Conversion extends BaseConversion implements Conversion{

    public FromString2JodaTime1x4Conversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (!Import2.JODA_TIME_1X4) {
            return;
        }
        for (String classname : Collect2.list(AliasConstant2.Joda_Years_ClassName,
            AliasConstant2.Joda_Months_ClassName,
            AliasConstant2.Joda_Weeks_ClassName,
            AliasConstant2.Joda_Days_ClassName,
            AliasConstant2.Joda_Hours_ClassName,
            AliasConstant2.Joda_Minutes_ClassName,
            AliasConstant2.Joda_Seconds_ClassName)) {
            registry.register(CLASS_String, classname, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String var = defineGetterValueVar(scripts, getter);
        onNull(scripts, setter, var).or("{}.parse{}({})",
            Imported.nameOf(setterActualType),
            Element2.getSimpleName(setterActualType),
            var);
    }
}
