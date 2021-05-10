package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

import javax.lang.model.element.Modifier;
import java.util.Map;

/**
 * @author benshaoye
 */
public class JavaElemField extends BaseBlockCommentable {

    private final JavaScopedMethods scopedMethods;
    private final boolean inInterface;
    private final String fieldType;
    private final String fieldName;

    private Map<String, JavaElemMethod> setterMethodMap;
    private JavaElemMethod getterMethod;

    public JavaElemField(
        Importer importer, JavaScopedMethods scopedMethods, String fieldType, String fieldName, boolean inInterface
    ) {
        super(importer, JavaElementEnum.FIELD);
        this.scopedMethods = scopedMethods;
        this.inInterface = inInterface;
        this.fieldType = fieldType;
        this.fieldName = fieldName;
    }

    public boolean inInterface() { return inInterface; }

    @Override
    protected boolean isAllowModifierWith(Modifier modifier) {
        return inInterface() ? modifier == Modifier.STATIC : Modifier2.CLASS_FIELD_MODIFIERS.contains(modifier);
    }

    public JavaElemField withNonGetterMethod() {
        if (getterMethod != null) {
            scopedMethods.remove(getterMethod);
        }
        return this;
    }

    public JavaElemField withNonSetterMethod() {
        if (setterMethodMap != null) {
            setterMethodMap.values().forEach(scopedMethods::remove);
        }
        return this;
    }
}
