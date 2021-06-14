package com.moonsky.processing.converter;

import com.moonsky.processing.declared.PropertyMethodDeclared;
import com.moonsky.processing.generate.JavaCodeMethodBlockAddr;

/**
 * @author benshaoye
 */
public class EnumFromStringConverter extends BaseConverter {

    public EnumFromStringConverter() { super("{}.{}({} == null ? null : {}.valueOf({}))"); }

    @Override
    public void doConvert(
        JavaCodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String var = defineGetterValueVar(scripts, getter);
        scripts.scriptOf(template, THAT, nameOf(setter), var, typeOf(setter), var);
    }
}
