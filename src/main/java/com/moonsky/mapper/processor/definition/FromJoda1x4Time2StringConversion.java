package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public class FromJoda1x4Time2StringConversion extends BaseConversion implements Conversion {

    public FromJoda1x4Time2StringConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        for (String joda1x4Class : JODA_1x4_CLASSES) {
            registry.register(joda1x4Class, CLASS_String, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "{}.toString()", AS_ORIGINAL_ARGS);
    }
}
