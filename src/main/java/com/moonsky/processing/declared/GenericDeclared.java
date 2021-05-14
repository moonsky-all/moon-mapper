package com.moonsky.processing.declared;

import com.moonsky.processing.util.Generic2;

import java.util.Objects;

/**
 * @author benshaoye
 */
public class GenericDeclared extends TypeDeclared {

    /**
     * 泛型边界，如：java.util.function.Supplier&lt;T>
     * <p>
     * T 的边界为: java.lang.Object
     */
    private final String bound;
    /**
     * 有效类，结合 declare、actual、bound 计算出来的，最后使用
     * 一般不直接用 declare、actual、bound
     * <p>
     * 而是使用{@code effectType}
     */
    private final String effectType;
    /**
     * 如: java.util.List&lt;java.lang.String>  返回 java.util.List
     */
    private final String simpleType;

    public GenericDeclared(String declare, String actual, String bound) {
        super(declare, actual);
        this.bound = bound;
        boolean isBound = actual == null || Objects.equals(actual, declare);
        this.effectType = isBound ? bound : actual;
        this.simpleType = Generic2.typeSimplify(this.effectType);
    }

    public String getBound() { return bound; }

    @Override
    public String getEffectType() { return effectType; }

    @Override
    public String getSimpleType() { return simpleType; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeclareGeneric{");
        sb.append("declare='").append(getDeclare()).append('\'');
        sb.append(", actual='").append(getActual()).append('\'');
        sb.append(", bound='").append(getBound()).append('\'');
        sb.append(", effectType='").append(getEffectType()).append('\'');
        sb.append(", simpleType='").append(getSimpleType()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
