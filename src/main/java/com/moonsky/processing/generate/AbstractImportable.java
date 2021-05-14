package com.moonsky.processing.generate;

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

    public final String onImported(String classname) { return getImporter().onImported(classname); }

    public final String onImported(TypeElement type) { return getImporter().onImported(type); }

    public final String onImported(TypeMirror type) { return getImporter().onImported(type); }

    public final String onImported(Class<?> type) { return getImporter().onImported(type); }
}
