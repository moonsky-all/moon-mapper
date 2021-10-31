package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;

import java.util.function.Predicate;

/**
 * @author benshaoye
 */
final class MatchesConversion implements Conversion {

    private final Predicate<String> tester;
    private final Conversion conversion;

    public MatchesConversion(Predicate<String> tester, Conversion conversion) {
        this.conversion = conversion;
        this.tester = tester;
    }

    @Override
    public void register(ConversionRegistry registry) {}

    boolean matches(String getterActualType) {
        return tester.test(getterActualType);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {conversion.doMapping(scripts, getter, setter);}
}
