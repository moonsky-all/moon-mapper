package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;

/**
 * @author benshaoye
 */
public abstract class AbstractEndingImportable<T> extends AbstractImportable {

    private final T ending;

    public AbstractEndingImportable(Importer importer, T ending) {
        super(importer);
        this.ending = ending;
    }

    public final T end() { return ending; }
}
