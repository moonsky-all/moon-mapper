package com.moonsky.processing.declared;

import com.moonsky.processing.holder.Holders;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static com.moonsky.processing.util.Generic2.typeSimplify;

/**
 * @author benshaoye
 */
public class ParameterDeclared extends BaseDeclarable {

    private final ExecutableElement executableElement;
    private final String parameterName;
    private final String declaredType;
    private final String actualType;
    private final String simplifyActualType;
    private final int parameterIndex;

    protected ParameterDeclared(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        GenericsMap thisGenericsMap,
        ExecutableElement executableElement,
        VariableElement parameter,
        int parameterIndex
    ) {
        super(holders, thisElement, enclosingElement, thisGenericsMap);
        this.executableElement = executableElement;
        this.parameterName = parameter.getSimpleName().toString();
        String declaredType = parameter.asType().toString();
        String actualType = getMappedActual(declaredType);

        this.simplifyActualType = typeSimplify(actualType);
        this.parameterIndex = parameterIndex;
        this.declaredType = declaredType;
        this.actualType = actualType;
    }

    public TypeDeclared toTypeDeclared() {
        return new TypeDeclared(getDeclaredType(), getActualType());
    }

    public String getParameterName() { return parameterName; }

    public String getActualType() { return actualType; }

    public ExecutableElement getExecutableElement() { return executableElement; }

    public String getDeclaredType() { return declaredType; }

    public String getSimplifyActualType() { return simplifyActualType; }

    public int getParameterIndex() { return parameterIndex; }
}
