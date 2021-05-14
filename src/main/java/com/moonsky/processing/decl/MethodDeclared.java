package com.moonsky.processing.decl;

import com.moonsky.processing.holder.Holders;

/**
 * @author benshaoye
 */
public class MethodDeclared extends ExecutableDeclared {

    private final String methodName;

    protected MethodDeclared(Holders holders, String methodName) {
        super(holders);
        this.methodName = methodName;
    }

    public final String getMethodName() { return methodName; }

    public final String getReturnActualType() {
        throw new UnsupportedOperationException();
    }
}
