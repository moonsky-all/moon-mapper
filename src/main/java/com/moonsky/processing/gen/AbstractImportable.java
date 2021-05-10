package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

/**
 * 可 import 对象
 *
 * @author benshaoye
 */
public abstract class AbstractImportable {

    private final Importer importer;

    public AbstractImportable(Importer importer) {
        this.importer = importer;
    }

    protected final Importer getImporter() { return importer; }
}
