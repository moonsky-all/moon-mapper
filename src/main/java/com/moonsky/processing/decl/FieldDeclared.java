package com.moonsky.processing.decl;

import com.moonsky.processing.holder.AbstractHolder;
import com.moonsky.processing.holder.Holders;

/**
 * @author benshaoye
 */
public class FieldDeclared extends AbstractHolder {

    private final String name;
    private final String actualType;

    protected FieldDeclared(Holders holders, String name, String actualType) {
        super(holders);
        this.name = name;
        this.actualType = actualType;
    }

    public String getActualType() { return actualType; }
}
