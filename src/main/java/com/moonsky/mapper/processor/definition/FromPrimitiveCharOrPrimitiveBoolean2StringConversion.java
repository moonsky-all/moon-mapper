package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Imported;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.THIS;

/**
 * @author benshaoye
 */
public class FromPrimitiveCharOrPrimitiveBoolean2StringConversion extends BaseConversion implements Conversion {

    public FromPrimitiveCharOrPrimitiveBoolean2StringConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(PRIMITIVE_boolean, CLASS_String, this);
        registry.register(PRIMITIVE_char, CLASS_String, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        scripts.scriptOf("{}.{}({}.valueOf({}.{}()))", THAT,

            setter.getMethodName(), Imported.STRING, THIS, getter.getMethodName());
    }
}
