package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.generate.ElemField;
import com.moonsky.processor.processing.generate.VarSupplier;
import com.moonsky.processor.processing.util.*;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
public class FromString2JodaTime1x0Conversion extends BaseConversion implements Conversion {

    public FromString2JodaTime1x0Conversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        if (isNotImported1x0()) {
            return;
        }
        for (String classname : Collect2.list(AliasConstant2.Joda_DateTime_ClassName,
            AliasConstant2.Joda_MutableDateTime_ClassName)) {
            registry.register(CLASS_String, classname, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String var = defineGetterValueVar(scripts, getter);
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        String setterSimpleType = Element2.getSimpleName(setterActualType);
        if (String2.isBlank(pattern)) {
            VarSupplier<ElemField> fieldHelper = scripts.fieldsHelper();
            String formatterName = AliasConstant2.Joda_DateTimeFormatter_ClassName;
            String formatterKey = String2.keyOf(formatterName, setterActualType);
            String formatterConst = fieldHelper.nextConstVar(formatterKey);
            if (!fieldHelper.contains(formatterConst)) {
                String method = "dateTime";
                if (Import2.JODA_TIME_1X3) {
                    if (Test2.isTypeof(setterActualType, AliasConstant2.Joda_LocalDate_ClassName)) {
                        method = "date";
                    } else if (Test2.isTypeof(setterActualType, AliasConstant2.Joda_LocalTime_ClassName)) {
                        method = "time";
                    }
                }
                fieldHelper.declareField(formatterConst, formatterName)
                    .assign()
                    .valueOfFormatted("{}.{}()",
                        Imported.nameOf(AliasConstant2.Joda_ISODateTimeFormat_ClassName),
                        method);
            }
            onNull(scripts, setter, var).or("{}.parse{}({})", formatterConst, setterSimpleType, var);
        } else {
            String formatConst = defineJodaDateTimeFormatter(scripts, pattern);
            onNull(scripts, setter, var).or("{}.parse{}({})", formatConst, setterSimpleType, var);
        }
    }
}
