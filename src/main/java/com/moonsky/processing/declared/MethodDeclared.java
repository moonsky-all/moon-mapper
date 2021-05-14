package com.moonsky.processing.declared;

import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.String2;
import com.moonsky.processing.util.Test2;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author benshaoye
 */
public class MethodDeclared extends BaseExecuteDeclarable {

    private final String signature;
    private final TypeMirror returnType;
    private final TypeDeclared returnTypeDeclared;

    protected MethodDeclared(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        GenericsMap superGenericsMap,
        ExecutableElement executableElement,
        ExecutionEnum executionEnum
    ) {
        super(holders, thisElement, enclosingElement, superGenericsMap, executableElement, executionEnum);
        TypeMirror returnType = executableElement.getReturnType();
        this.returnType = returnType;
        String returnTypeStringify = returnType.toString();
        String returnActualType = getMappedActual(returnTypeStringify);
        this.returnTypeDeclared = new TypeDeclared(returnTypeStringify, returnActualType);
        this.signature = String2.format("{}({})", getMethodName(), super.getSignature());
    }

    public MethodDeclared(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        GenericsMap superGenericsMap,
        ExecutableElement executableElement
    ) {
        this(holders, thisElement, enclosingElement, superGenericsMap, executableElement, ExecutionEnum.METHOD);
    }

    public TypeMirror getReturnType() { return returnType; }

    public TypeDeclared getReturnTypeDeclared() { return returnTypeDeclared; }

    @Override
    public String getSignature() { return signature; }
}
