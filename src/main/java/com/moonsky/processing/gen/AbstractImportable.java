package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * 可 import 对象
 *
 * @author benshaoye
 */
public abstract class AbstractImportable {

    private final Importer importer;

    public AbstractImportable(Importer importer) { this.importer = importer; }

    protected final Importer getImporter() { return importer; }

    protected final String onImported(String classname) {
        return getImporter().onImported(classname);
    }

    protected final String onImported(Class<?> type) {
        return getImporter().onImported(type);
    }

    protected final String onImported(TypeElement type) {
        return getImporter().onImported(type);
    }

    protected final String onImported(TypeMirror type) {
        return getImporter().onImported(type);
    }
}
