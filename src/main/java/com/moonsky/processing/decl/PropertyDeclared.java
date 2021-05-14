package com.moonsky.processing.decl;

import com.moonsky.processing.holder.AbstractHolder;
import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.Element2;
import com.moonsky.processing.util.String2;

import javax.lang.model.element.TypeElement;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public class PropertyDeclared extends AbstractHolder {

    private final String name;
    private final TypeElement thisElement;
    private final TypeElement enclosingElement;
    private final String thisClassname;
    private final String enclosingClassname;
    private final TypeDeclared typeDeclared;
    private final Map<String, GenericDeclared> thisGenericsMap;

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
        String propertyName,
        TypeElement thisElement,
        TypeElement enclosingElement,
        TypeDeclared typeDeclared
    ) {
        super(holders);
        this.thisElement = thisElement;
        this.enclosingElement = enclosingElement;
        this.typeDeclared = typeDeclared;
        this.name = propertyName;
        this.thisGenericsMap = typeDeclared.getThisGenericsMap();
        this.thisClassname = Element2.getQualifiedName(thisElement);
        this.enclosingClassname = Element2.getQualifiedName(enclosingElement);
    }

    public String getGetterReffed(String instanceName) {
        return instanceName + "." + getOriginGetterDeclared().getMethodName() + "()";
    }

    public String getSetterReffed(String instanceName, String parameterName) {
        return instanceName + "." + getOriginSetterDeclared().getMethodName() + "(" + parameterName + ")";
    }

    public boolean isReadable() { return getOriginGetterDeclared() != null; }

    public boolean isWritable() { return getOriginSetterDeclared() != null; }

    public boolean isWritable(String setterActualClass, Object... types) {
        String setterActual = String2.format(setterActualClass, types);
        return getOriginTypedSetterMap().containsKey(setterActual);
    }

    public boolean hasField() { return getOriginFieldDeclared() != null; }

    public String getPropertyType() {
        if (getOriginGetterDeclared() != null) {
            return getOriginGetterDeclared().getPropertyActualType();
        }
        if (getOriginSetterDeclared() != null) {
            return getOriginSetterDeclared().getPropertyActualType();
        }
        if (getOriginFieldDeclared() != null) {
            return getOriginFieldDeclared().getActualType();
        }
        return null;
    }

    public String getName() { return name; }

    public TypeElement getThisElement() { return thisElement; }

    public TypeElement getEnclosingElement() { return enclosingElement; }

    public String getThisClassname() { return thisClassname; }

    public String getEnclosingClassname() { return enclosingClassname; }

    public Map<String, GenericDeclared> getThisGenericsMap() { return thisGenericsMap; }

    public Map<String, PropertyMethodDeclared> getTypedSetterMap() {
        return new LinkedHashMap<>(getOriginTypedSetterMap());
    }

    private Map<String, PropertyMethodDeclared> getOriginTypedSetterMap() {
        return typedSetterMap;
    }

    private PropertyFieldDeclared getOriginFieldDeclared() { return fieldDeclared; }

    private TypeDeclared getOriginTypeDeclared() { return typeDeclared; }

    private PropertyMethodDeclared getOriginGetterDeclared() { return getterDeclared; }

    private PropertyMethodDeclared getOriginSetterDeclared() { return setterDeclared; }
}
