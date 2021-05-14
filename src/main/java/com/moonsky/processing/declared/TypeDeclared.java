package com.moonsky.processing.declared;

import com.moonsky.processing.util.Generic2;

/**
 * @author benshaoye
 */
public class TypeDeclared {

    /**
     * 泛型声明类型，如：T
     */
    private final String declare;
    /**
     * 泛型实际类型：如：java.lang.String
     */
    private final String actual;
    /**
     * 如: java.util.List&lt;java.lang.String>  返回 java.util.List
     */
    private final String simpleType;

    public TypeDeclared(String declare, String actual) {
        this.declare = declare;
        this.actual = actual;
        this.simpleType = Generic2.typeSimplify(actual);
    }

    public final String getDeclare() { return declare; }

    public final String getActual() { return actual; }

    public String getEffectType() { return getActual(); }

    public String getSimpleType() { return simpleType; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TypeDeclared{");
        sb.append("declare='").append(declare).append('\'');
        sb.append(", actual='").append(actual).append('\'');
        sb.append(", simpleType='").append(simpleType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
