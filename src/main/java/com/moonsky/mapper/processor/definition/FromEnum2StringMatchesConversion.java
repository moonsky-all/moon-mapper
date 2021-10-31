package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Test2;

/**
 * @author benshaoye
 */
public class FromEnum2StringMatchesConversion extends BaseConversion implements Conversion {

    public FromEnum2StringMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.registerMatches(Test2::isEnumClass, CLASS_String, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "{}.name()", AS_ORIGINAL_ARGS);
    }
}
