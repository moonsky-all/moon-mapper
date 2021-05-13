package com.moonsky.processing.decl;

import com.moonsky.processing.holder.Holders;

/**
 * @author benshaoye
 */
public class PropertyMethodDeclared extends MethodDeclared {

    private final MethodType methodType;

    protected PropertyMethodDeclared(Holders holders, String methodName, MethodType methodType) {
        super(holders, methodName);
        this.methodType = methodType;
    }

    public MethodType getMethodType() { return methodType; }

    public String getPropertyActualType() {
        return getMethodType().getPropertyType(this);
    }

    public enum MethodType {
        /** 类型 */
        SETTER {
            @Override
            String getPropertyType(PropertyMethodDeclared methodDeclared) {
                return methodDeclared.getParameterAt(0).getActualType();
            }
        },
        GETTER {
            @Override
            String getPropertyType(PropertyMethodDeclared methodDeclared) {
                return methodDeclared.getReturnActualType();
            }
        };

        abstract String getPropertyType(PropertyMethodDeclared methodDeclared);
    }
}
