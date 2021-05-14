package com.moonsky.processing.declared;

import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.Element2;
import com.moonsky.processing.util.Generic2;
import com.moonsky.processing.util.String2;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public class PropertyDeclared extends BaseDeclarable {

    private final String name;
    private final ClassDeclared typeDeclared;

    private PropertyFieldDeclared fieldDeclared;
    /**
     * setter 方法，要求 setter 方法参数类型和 getter 方法返回值类型一致
     * <p>
     * 若没有 getter 方法，且存在 setter 重载时，setter 方法取用优先规则为:
     * 1. 与字段声明类型一致
     * 2. 按特定顺序排序后的第一个方法（这种排序规则受 jdk 版本可能受 jvm 版本的影响，所以这里自定义顺序）
     */
    private final Map<String, PropertyMethodDeclared> typedSetterMap = new LinkedHashMap<>();
    /**
     * 由于 java 方法重载规则， getter 方法只有一个，所以不考虑重载的情况
     * <p>
     * 暂时也不考虑 getXxx(int) 的情况，根据个人经验，从未遇到过这种情况
     * <p>
     * 如果实际应用中即使真的出现 getXxx(int) 的 getter，请考虑修改成其他方案
     */
    private PropertyMethodDeclared getterDeclared;
    /**
     * 从{@link #typedSetterMap}最终选用的 setter 方法
     *
     * <pre>
     * 1. 如果存在 getter，setter 参数类型与 getter 返回值类型一致
     * 2. 若不存在 getter，存在字段，setter 参数类型与字段类型一致
     * 3. 若没有与字段类型一致的 setter 方法就按照一定规则取一个默认的 setter 方法
     * </pre>
     */
    private PropertyMethodDeclared setterDeclared;

    protected PropertyDeclared(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        String propertyName,
        ClassDeclared typeDeclared
    ) {
        super(holders, thisElement, enclosingElement, typeDeclared.getThisGenericsMap());
        this.typeDeclared = typeDeclared;
        this.name = propertyName;
    }

    /*
     * -------------------------------------------------------------------------------------------
     * 方法引用语句
     * -------------------------------------------------------------------------------------------
     */

    /**
     * getter 引用
     *
     * @param instanceName
     *
     * @return
     */
    public String getGetterReferenced(String instanceName) {
        return isReadable() ? (instanceName + "." + getOriginGetterDeclared().getMethodName() + "()") : "";
    }

    /**
     * setter 引用
     *
     * @param instanceName
     * @param parameterName
     *
     * @return
     */
    public String getSetterReferenced(String instanceName, String parameterName) {
        return toSetterReferenced(getOriginSetterDeclared(), instanceName, parameterName);
    }

    public String getSetterReferenced(
        String instanceName, String parameterName, String actualTypeTemplate, Object... types
    ) {
        return toSetterReferenced(getSetterMethod(actualTypeTemplate, types), instanceName, parameterName);
    }

    public String getSetterReferenced(
        String instanceName, String parameterName, Class<?> klass
    ) {
        return toSetterReferenced(getSetterMethod(klass), instanceName, parameterName);
    }

    /*
     * -------------------------------------------------------------------------------------------
     * 判断
     * -------------------------------------------------------------------------------------------
     */

    public boolean isReadable() { return getOriginGetterDeclared() != null; }

    public boolean isWritable() { return getOriginSetterDeclared() != null; }

    public boolean isWritable(String setterActualClass, Object... types) {
        String setterActual = String2.typeFormatted(setterActualClass, types);
        return getOriginTypedSetterMap().containsKey(setterActual);
    }

    public boolean isWritable(Class<?> setterClass) {
        return getOriginTypedSetterMap().containsKey(setterClass.getCanonicalName());
    }

    public boolean hasField() { return getOriginFieldDeclared() != null; }

    /*
     * -------------------------------------------------------------------------------------------
     * 计算属性/方法
     * -------------------------------------------------------------------------------------------
     */

    public PropertyMethodDeclared getSetterMethod(String setterActualClass, Object... types) {
        return getOriginTypedSetterMap().get(String2.typeFormatted(setterActualClass, types));
    }

    public PropertyMethodDeclared getSetterMethod(Class<?> setterClass) {
        return getOriginTypedSetterMap().get(setterClass.getCanonicalName());
    }

    public String getPropertyType() {
        if (getOriginGetterDeclared() != null) {
            return getOriginGetterDeclared().getPropertyActualType();
        }
        if (getOriginSetterDeclared() != null) {
            return getOriginSetterDeclared().getPropertyActualType();
        }
        if (getOriginFieldDeclared() != null) {
            return getOriginFieldDeclared().getType();
        }
        return null;
    }

    public Map<String, PropertyMethodDeclared> getTypedSetterMap() {
        return new LinkedHashMap<>(getOriginTypedSetterMap());
    }

    /*
     * -------------------------------------------------------------------------------------------
     * setter & getter
     * -------------------------------------------------------------------------------------------
     */

    public String getName() { return name; }

    private Map<String, PropertyMethodDeclared> getOriginTypedSetterMap() { return typedSetterMap; }

    private PropertyFieldDeclared getOriginFieldDeclared() { return fieldDeclared; }

    private ClassDeclared getOriginTypeDeclared() { return typeDeclared; }

    private PropertyMethodDeclared getOriginGetterDeclared() { return getterDeclared; }

    private PropertyMethodDeclared getOriginSetterDeclared() { return setterDeclared; }

    /*
     * -------------------------------------------------------------------------------------------
     * tools
     * -------------------------------------------------------------------------------------------
     */

    private static String toSetterReferenced(PropertyMethodDeclared setter, String instanceName, String parameterName) {
        return setter == null ? "" : (instanceName + "." + setter.getMethodName() + "(" + parameterName + ")");
    }

    void withGetter(ExecutableElement elem) {
        if (this.getterDeclared != null) {
            return;
        }
        this.getterDeclared = new PropertyMethodDeclared(getHolders(),
            getThisElement(),
            (TypeElement) elem.getEnclosingElement(),
            getThisGenericsMap(),
            elem,
            Element2.getSimpleName(elem));
    }

    void withTypedSetterIfAbsent(ExecutableElement elem, String actualType) {
        String simplifySetterType = Generic2.typeSimplify(actualType);
        PropertyMethodDeclared setter = typedSetterMap.get(simplifySetterType);
        if (setter != null) {
            return;
        }
        typedSetterMap.putIfAbsent(simplifySetterType,
            new PropertyMethodDeclared(getHolders(),
                getThisElement(),
                (TypeElement) elem.getEnclosingElement(),
                getThisGenericsMap(),
                elem,
                Element2.getSimpleName(elem)));
    }

    void withFieldIfAbsent(VariableElement element) {
        if (fieldDeclared != null) {
            return;
        }
        this.fieldDeclared = new PropertyFieldDeclared(getHolders(),
            getThisElement(),
            ((TypeElement) element.getEnclosingElement()),
            getThisGenericsMap(),
            element);
    }

    public void onCompleted() {
        throw new UnsupportedOperationException();
        // computeGetterMethod();
        // computeSetterMethod();
    }
}
