package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public class FromObject2StringDowngradesConversion extends BaseConversion implements Conversion {

    public FromObject2StringDowngradesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.registerDowngrades(CLASS_String, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "{}.toString()", AS_ORIGINAL_ARGS);
    }
}
