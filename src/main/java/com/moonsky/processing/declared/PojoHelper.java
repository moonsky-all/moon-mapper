package com.moonsky.processing.declared;

import com.moonsky.processing.holder.AbstractHolder;

/**
 * @author benshaoye
 */
final class PojoHelper extends AbstractHolder {

    private final PojoDeclared pojoDeclared;

    PojoHelper(ClassDeclared classDeclared) {
        super(classDeclared.getHolders());
        this.pojoDeclared = new PojoDeclared(classDeclared);
    }

    public PojoDeclared toPojoDeclared() {
        return pojoDeclared;
    }
}
