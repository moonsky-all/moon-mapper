package com.moonsky.processing.declared;

import com.moonsky.processing.holder.Holders;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * @author benshaoye
 */
public class PropertyFieldDeclared extends FieldDeclared {

    protected PropertyFieldDeclared(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        GenericsMap thisGenericsMap,
        VariableElement fieldElement
    ) {
        super(holders, thisElement, enclosingElement, thisGenericsMap, fieldElement);
    }
}
