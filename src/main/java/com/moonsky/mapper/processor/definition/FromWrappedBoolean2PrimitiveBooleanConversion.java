package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import static com.moonsky.mapper.processor.definition.ConversionUtils.THAT;
import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;

/**
 * @author benshaoye
 */
public class FromWrappedBoolean2PrimitiveBooleanConversion extends BaseConversion implements Conversion {

    public FromWrappedBoolean2PrimitiveBooleanConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_Boolean, PRIMITIVE_boolean, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        scripts.scriptOf("{}.{}({} != null && {})", THAT, setter.getMethodName(), var, var);
    }
}
