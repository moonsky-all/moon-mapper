package com.moonsky.processing.decl;

import com.moonsky.processing.holder.AbstractHolder;
import com.moonsky.processing.holder.Holders;

/**
 * @author benshaoye
 */
public class ParameterDeclared extends AbstractHolder {

    protected ParameterDeclared(Holders holders) {
        super(holders);
    }

    public String getActualType() {
        throw new UnsupportedOperationException();
    }
}
