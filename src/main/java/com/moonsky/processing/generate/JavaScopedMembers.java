package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public abstract class JavaScopedMembers<M> extends AbstractImportable {

    private final JavaGenericsList enclosingGenericsList;
    private final boolean inInterface;
    private final Map<String, M> memberMap = new LinkedHashMap<>();

    public JavaScopedMembers(Importer importer, JavaGenericsList enclosingGenericsList, boolean inInterface) {
        super(importer);
        this.inInterface = inInterface;
        this.enclosingGenericsList = enclosingGenericsList;
    }

    protected final Map<String, M> getMemberMap() { return memberMap; }

    protected final JavaGenericsList getEnclosingGenericsList() { return enclosingGenericsList; }

    protected final boolean inInterface() { return inInterface; }

    public JavaScopedMembers<M> remove(String signature) {
        getMemberMap().remove(signature);
        return this;
    }
}
