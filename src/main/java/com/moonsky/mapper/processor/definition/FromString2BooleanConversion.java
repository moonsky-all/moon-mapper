package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Imported;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.THIS;

/**
 * @author benshaoye
 */
public class FromString2BooleanConversion extends BaseConversion implements Conversion {

    public FromString2BooleanConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_String, PRIMITIVE_boolean, this);
        registry.register(CLASS_String, CLASS_Boolean, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        scripts.scriptOf("{}.{}({}.parseBoolean({}.{}()))",
            THAT,
            setter.getMethodName(),
            Imported.of(Boolean.class),
            THIS,
            getter.getMethodName());
    }
}
