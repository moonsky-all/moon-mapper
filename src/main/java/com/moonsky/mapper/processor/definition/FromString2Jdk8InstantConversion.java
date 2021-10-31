package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Imported;

import java.time.Instant;

/**
 * @author benshaoye
 */
public class FromString2Jdk8InstantConversion extends BaseConversion implements Conversion {

    public FromString2Jdk8InstantConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(CLASS_String, classname(Instant.class), this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        onNullOr(scripts, getter, setter, "{}.parse({})", var -> objectsOf(Imported.of(Instant.class), var));
    }
}
