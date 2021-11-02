package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.generate.ElemField;
import com.moonsky.processor.processing.generate.ElemFieldValue;
import com.moonsky.processor.processing.generate.VarSupplier;
import com.moonsky.processor.processing.util.Element2;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.String2;

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
        for (String classname : JODA_1x0_CLASSES) {
            registry.register(CLASS_String, classname, this);
        }
    }

    private String getIsoFormatMethodName(String setterActualType) {
        if (IMPORTED_Joda1x3) {
            if (CLASS_Joda_LocalDate.equals(setterActualType)) {
                return "date";
            }
            if (CLASS_Joda_LocalTime.equals(setterActualType)) {
                return "time";
            }
        }
        return "dateTime";
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
            String formatterName = CLASS_Joda_DateTimeFormatter;
            String formatterKey = String2.keyOf(formatterName, setterActualType);
            String formatterConst = fieldHelper.nextConstVar(formatterKey);
            if (!fieldHelper.contains(formatterConst)) {
                ElemFieldValue valuer = fieldHelper.declareField(formatterConst, formatterName).assign();
                valuer.valueOfFormatted("{}.{}()",
                    Imported.nameOf(CLASS_Joda_ISODateTimeFormat),
                    getIsoFormatMethodName(setterActualType));
            }
            onNull(scripts, setter, var).or("{}.parse{}({})", formatterConst, setterSimpleType, var);
        } else {
            String formatConst = defineJodaDateTimeFormatter(scripts, pattern);
            onNull(scripts, setter, var).or("{}.parse{}({})", formatConst, setterSimpleType, var);
        }
    }
}
