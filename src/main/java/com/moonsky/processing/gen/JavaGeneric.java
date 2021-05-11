package com.moonsky.processing.gen;

import com.moonsky.processing.util.Generic2;
import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;

/**
 * @author benshaoye
 */
public class JavaGeneric extends AbstractImportable implements Addable {

    private final String declare;
    private final String bound;
    private final String typeSimplify;

    public JavaGeneric(Importer importer, String declare, String bound) {
        super(importer);
        this.declare = declare;
        this.bound = bound;
        this.typeSimplify = Generic2.typeSimplify(bound);
    }

    public String getDeclare() { return declare; }

    public String getBound() { return bound; }

    public String getTypeSimplify() { return typeSimplify; }

    @Override
    public void add(JavaAddr addr) {
        addr.add(declare);
        if (String2.isNotBlank(bound)) {
            addr.add(" extends ").add(bound);
        }
    }
}
