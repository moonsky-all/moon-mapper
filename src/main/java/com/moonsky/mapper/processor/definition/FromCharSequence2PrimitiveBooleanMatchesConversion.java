package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Imported;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
public class FromCharSequence2PrimitiveBooleanMatchesConversion extends BaseConversion implements Conversion {

    public FromCharSequence2PrimitiveBooleanMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        String fromClassname = classname(CharSequence.class);
        registry.register(fromClassname, PRIMITIVE_boolean, this);
        registry.register(classname(StringBuffer.class), PRIMITIVE_boolean, this);
        registry.register(classname(StringBuilder.class), PRIMITIVE_boolean, this);
        registry.registerMatches(testerSubtypeOf(fromClassname), PRIMITIVE_boolean, this);

        registry.register(fromClassname, CLASS_Boolean, this);
        registry.register(classname(StringBuffer.class), CLASS_Boolean, this);
        registry.register(classname(StringBuilder.class), CLASS_Boolean, this);
        registry.registerMatches(testerSubtypeOf(fromClassname), CLASS_Boolean, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String template = "{}.{}({} != null && {}.parseBoolean({}.toString()))";
        String var = defineGetterValueVar(scripts, getter);
        scripts.scriptOf(template, THAT, setter.getMethodName(), var, Imported.of(Boolean.class), var);
    }
}
