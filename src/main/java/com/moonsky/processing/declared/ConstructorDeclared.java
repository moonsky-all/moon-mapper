package com.moonsky.processing.declared;

import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.String2;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * @author benshaoye
 */
public class ConstructorDeclared extends BaseExecuteDeclarable {

    private final String signature;

    protected ConstructorDeclared(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        GenericsMap superGenericsMap,
        ExecutableElement executableElement
    ) {
        super(holders, thisElement, enclosingElement, superGenericsMap, executableElement, ExecutionEnum.CONSTRUCTOR);
        this.signature = String2.format("({})", super.getSignature());
    }

    @Override
    public String getSignature() { return signature; }
}
