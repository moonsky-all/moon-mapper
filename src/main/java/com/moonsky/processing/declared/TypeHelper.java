package com.moonsky.processing.declared;

import com.moonsky.processing.holder.AbstractHolder;
import com.moonsky.processing.holder.Holders;

import javax.lang.model.element.TypeElement;

/**
 * @author benshaoye
 */
public class TypeHelper extends AbstractHolder {

    private final TypeDeclared typeDeclared;

    protected TypeHelper(Holders holders, TypeElement typeElement) {
        super(holders);
        this.typeDeclared = new TypeDeclared(holders, typeElement);
    }

    public TypeDeclared doParseTypeDeclared() {
        return typeDeclared;
    }
}
