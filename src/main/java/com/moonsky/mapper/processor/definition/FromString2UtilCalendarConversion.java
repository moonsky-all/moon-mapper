package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.AliasConstant2;
import com.moonsky.processor.processing.util.Stringify;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
public class FromString2UtilCalendarConversion extends BaseConversion implements Conversion {

    public FromString2UtilCalendarConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_util_Calendar, CLASS_String, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        String pattern = getFormatOrDefaultIfBlank(setter, getter, AliasConstant2.PATTERN_DATETIME);
        onNull(scripts, setter, var).or("{}.parseCalendar({}, {})", FORMATTER_IMPORTED, var, Stringify.of(pattern));
    }
}
