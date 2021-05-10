package com.moonsky.processing.gen;

import java.util.function.BooleanSupplier;

/**
 * @author benshaoye
 */
public class JavaTempTester {

    private final BooleanSupplier supplier;

    public JavaTempTester(BooleanSupplier supplier) {
        this.supplier = supplier;
    }

    public boolean isMatched() {
        return supplier.getAsBoolean();
    }
}
