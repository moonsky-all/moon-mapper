package com.moonsky.processing.generate;

import com.moonsky.processing.holder.ValueHolder;
import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;

import javax.lang.model.element.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author benshaoye
 */
public class JavaElemField extends BaseBlockCommentable {

    private final JavaScopedMethods scopedMethods;
    private final boolean inInterface;
    private final String fieldType;
    private final String fieldName;

    private final Map<String, JavaElemMethod> setterMethodMap = new LinkedHashMap<>();
    private JavaElemMethod getterMethod;

    private JavaElemFieldValue value;

    public JavaElemField(
        Importer importer, JavaScopedMethods scopedMethods, String fieldType, String fieldName, boolean inInterface
    ) {
        super(importer, JavaElementEnum.FIELD);
        this.scopedMethods = scopedMethods;
        this.inInterface = inInterface;
        this.fieldType = fieldType;
        this.fieldName = fieldName;
    }

    protected JavaElemFieldValue getValue() { return value; }

    public JavaElemFieldValue assign() {
        return value == null ? (value = new JavaElemFieldValue(this, fieldType)) : value;
    }

    public boolean inInterface() { return inInterface; }

    @Override
    protected boolean isAllowModifierWith(Modifier modifier) {
        return !inInterface() && Modifier2.CLASS_FIELD_MODIFIERS.contains(modifier);
    }

    public JavaElemMethod withDefaultSetValue4SetterMethod(JavaElemMethod setterMethod) {
        if (setterMethodMap.containsValue(setterMethod)) {
            // TODO this.fieldName = fieldName;
        }
        return setterMethod;
    }

    public JavaElemMethod withDefaultSetterMethod() {
        JavaElemMethod setterMethod = withSetterMethod();
        withDefaultSetValue4SetterMethod(setterMethod);
        return setterMethod;
    }

    public JavaElemMethod withSetterMethod() {
        return withSetterMethod(fieldName, fieldType);
    }

    public JavaElemMethod withSetterMethod(String parameterName, String parameterTypeTemplate, Object... types) {
        String setterName = String2.toSetterName(fieldName);
        ValueHolder<JavaElemParameter> parameterHolder = new ValueHolder<>();
        JavaElemMethod setterMethod = scopedMethods.declareMethod(setterName, parameterBuilder -> {
            parameterBuilder.add(parameterName, parameterHolder::set, parameterTypeTemplate, types);
        }).typeOf("void");
        String formattedParameterType = parameterHolder.get().getParameterType();
        setterMethodMap.put(formattedParameterType, setterMethod);
        return setterMethod;
    }

    public JavaElemField returningDefault4GetterMethod() {
        if (getterMethod != null) {
            getterMethod.scripts().returning(this.fieldName);
        }
        return this;
    }

    public JavaElemMethod withGetterMethod() { return withGetterMethod(fieldType); }

    public JavaElemMethod withGetterMethod(String returnTypeTemplate, Object... types) {
        return withGetterMethod(g -> {}, returnTypeTemplate, types);
    }

    /**
     * 定义 getter 方法
     *
     * @param genericsBuilder
     * @param returnTypeTemplate
     * @param types
     *
     * @return
     */
    public JavaElemMethod withGetterMethod(
        Consumer<JavaGenericsList> genericsBuilder, String returnTypeTemplate, Object... types
    ) {
        withNonGetterMethod();
        String formatted = TypeFormatter2.with(returnTypeTemplate, types);
        String getterName = String2.toGetterName(fieldName, formatted);
        JavaElemMethod getterMethod = scopedMethods.declareMethod(getterName, genericsBuilder, p -> {});
        // 设置返回值类型
        getterMethod.typeOf(formatted);
        this.getterMethod = getterMethod;
        // 默认返回值
        if (Objects.equals(getterMethod.getReturnType(), fieldType)) {
            returningDefault4GetterMethod();
        }
        return getterMethod;
    }

    public JavaElemField withNonGetterMethod() {
        if (getterMethod != null) {
            scopedMethods.remove(getterMethod);
        }
        return this;
    }

    public JavaElemField withNonSetterMethod() {
        setterMethodMap.values().forEach(scopedMethods::remove);
        return this;
    }

    public void addDeclareField(JavaAddr addr) {
        if (inInterface()) {
            modifierWith(Modifier.STATIC);
        }
        addr.next();
        addDeclareBlockComments(addr);
        addDeclareDocComments(addr);
        addDeclareAnnotations(addr);
        addr.newLine("");
        if (addDeclareModifiers(addr)) {
            addr.add(' ');
        }
        String importedFieldType = onImported(fieldType);
        addr.add(importedFieldType).add(' ').add(fieldName);
        addDeclaredFieldValue(addr, importedFieldType);
        addr.end();
    }

    private void addDeclaredFieldValue(JavaAddr addr, String importedFieldType) {
        JavaElemFieldValue value = this.getValue();
        if (value != null && value.isAvailable()) {
            value.addFieldValue(addr);
        } else {
            addr.add(" = ").add(TypeFormatter2.defaultVal(importedFieldType));
        }
    }
}
