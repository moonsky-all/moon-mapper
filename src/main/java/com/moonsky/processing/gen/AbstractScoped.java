package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

/**
 * @author benshaoye
 */
public abstract class AbstractScoped<T> extends AbstractImportable {

    private final T value;

    public AbstractScoped(Importer importer, T value) {
        super(importer);
        this.value = value;
    }

    public final T end() {
        return value;
    }
}
