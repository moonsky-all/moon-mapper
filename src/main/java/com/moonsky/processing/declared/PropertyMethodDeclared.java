package com.moonsky.processing.declared;

import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.Const2;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import static com.moonsky.processing.declared.ExecutionEnum.GETTER_METHOD;
import static com.moonsky.processing.declared.ExecutionEnum.SETTER_METHOD;

/**
 * @author benshaoye
 */
public class PropertyMethodDeclared extends MethodDeclared {

    private final TypeDeclared propertyTypeDeclared;

    protected PropertyMethodDeclared(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        GenericsMap superGenericsMap,
        ExecutableElement executableElement,
        String methodName
    ) {
        super(holders,
            thisElement,
            enclosingElement,
            superGenericsMap,
            executableElement,
            methodName.startsWith(Const2.SET) ? SETTER_METHOD : GETTER_METHOD);
        this.propertyTypeDeclared = toPropertyTypeDeclared();
    }

    private TypeDeclared toPropertyTypeDeclared() {
        if (getExecutionEnum() == GETTER_METHOD) {
            return getReturnTypeDeclared();
        } else {
            return getParameterAt(0).toTypeDeclared();
        }
    }

    public String getPropertyActualType() { return getPropertyTypeDeclared().getActual(); }

    public TypeDeclared getPropertyTypeDeclared() { return propertyTypeDeclared; }
}
