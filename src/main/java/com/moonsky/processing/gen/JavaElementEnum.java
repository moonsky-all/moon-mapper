package com.moonsky.processing.gen;

import javax.lang.model.element.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 注解位置
 *
 * @author benshaoye
 */
public enum JavaElementEnum {
    /**
     * 类
     */
    CLASS(Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.FINAL),
    /**
     * 接口
     */
    INTERFACE(Modifier.PUBLIC),
    /**
     * 枚举
     */
    ENUM(Modifier.PUBLIC),
    /**
     * 字段
     */
    FIELD(Modifier.PUBLIC,
        Modifier.PROTECTED,
        Modifier.PRIVATE,
        Modifier.FINAL,
        Modifier.VOLATILE,
        Modifier.TRANSIENT,
        Modifier.STATIC),
    /**
     * 方法
     */
    METHOD(Modifier.PUBLIC,
        Modifier.PROTECTED,
        Modifier.PRIVATE,
        Modifier.STATIC,
        Modifier.FINAL,
        Modifier.ABSTRACT,
        Modifier.SYNCHRONIZED),
    /**
     * 参数
     */
    PARAMETER(Modifier.FINAL),
    /**
     * 本地变量
     */
    LOCAL_VAR(Modifier.FINAL),
    ;

    private final Set<Modifier> allowedModifierSet = new HashSet<>();

    JavaElementEnum(Modifier... modifiers) {
        if (modifiers != null) {
            allowedModifierSet.addAll(Arrays.asList(modifiers));
        }
    }

    public boolean isAllowModifierWith(Modifier modifier) {
        return allowedModifierSet.contains(modifier);
    }
}
