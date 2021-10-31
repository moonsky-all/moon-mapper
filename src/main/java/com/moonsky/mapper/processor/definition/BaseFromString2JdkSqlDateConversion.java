package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.Stringify;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
abstract class BaseFromString2JdkSqlDateConversion extends BaseConversion implements Conversion {

    private final String toClassname;
    private final String defaultPattern;

    public BaseFromString2JdkSqlDateConversion(String toClassname, String defaultPattern) {
        this.defaultPattern = defaultPattern;
        this.toClassname = toClassname;
    }

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_String, toClassname, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String pattern = getFormatOrDefaultIfBlank(setter, getter, defaultPattern);
        String var = defineGetterValueVar(scripts, getter);
        onNull(scripts, setter, var).or("new {}({}.parse({}, {}).getTime())",//
            Imported.nameOf(setter.getPropertyActualType()),//
            FORMATTER_IMPORTED, var, Stringify.of(pattern));
    }
}
