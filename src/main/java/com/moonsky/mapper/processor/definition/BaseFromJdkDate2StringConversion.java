package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Stringify;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
abstract class BaseFromJdkDate2StringConversion extends BaseConversion implements Conversion {

    private final String fromClassname;
    private final String defaultPattern;

    protected BaseFromJdkDate2StringConversion(
        String fromClassname, String defaultPattern
    ) {
        this.fromClassname = fromClassname;
        this.defaultPattern = defaultPattern;
    }

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(fromClassname, CLASS_String, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        Stringify stringify = Stringify.of(getFormatOrDefaultIfBlank(setter, getter, defaultPattern));
        String var = defineGetterValueVar(scripts, getter);
        onNull(scripts, setter, var).or("{}.format({}, {})", FORMATTER_IMPORTED, var, stringify);
    }
}
