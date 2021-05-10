package com.moonsky.processing.gen;

import com.moonsky.processing.util.Generic2;
import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.Processing2;

import javax.lang.model.element.Modifier;

/**
 * @author benshaoye
 */
public class JavaElemParameter extends BaseBlockCommentable {

    private final String parameterType;
    private final String parameterName;
    private final JavaTempTester staticTester;
    private final JavaGenericsList enclosingGenericsList;
    private final JavaGenericsList executableGenericsList;
    private final boolean maybeGenericType;

    public JavaElemParameter(
        Importer importer,
        JavaTempTester staticTester,
        JavaGenericsList enclosingGenericsList,
        JavaGenericsList executableGenericsList,
        String parameterName,
        String typeTemplate,
        Object... types
    ) {
        super(importer, JavaElementEnum.PARAMETER);
        this.enclosingGenericsList = enclosingGenericsList;
        this.executableGenericsList = executableGenericsList;
        this.staticTester = staticTester;
        this.parameterName = parameterName;
        this.parameterType = TypeFormatter2.with(typeTemplate, types);
        this.maybeGenericType = Processing2.getUtils().getTypeElement(parameterType) == null;
    }

    /**
     * 简化的类型，
     * type: java.util.List&lt;java.lang.String>
     * typeSimplify: java.util.List
     * <p>
     * 方法签名的重载判断依据是根据简化类型判断的
     */
    public String getSignature() {
        if (parameterType.contains(".")) {
            return Generic2.typeSimplify(parameterType);
        }
        if (maybeGenericType) {
            JavaGeneric executableGeneric = executableGenericsList.get(parameterType);
            if (executableGeneric != null) {
                return executableGeneric.getTypeSimplify();
            }
            if (staticTester.isMatched()) {
                return Object.class.getCanonicalName();
            }
            JavaGeneric enclosingGeneric = enclosingGenericsList.get(parameterType);
            if (enclosingGeneric != null) {
                return enclosingGeneric.getTypeSimplify();
            }
            return Object.class.getCanonicalName();
        }
        return parameterType;
    }

    @Override
    protected boolean isAllowModifierWith(Modifier modifier) {
        return modifier == Modifier.FINAL;
    }
}
