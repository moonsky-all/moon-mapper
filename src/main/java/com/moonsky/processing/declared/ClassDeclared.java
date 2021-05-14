package com.moonsky.processing.declared;

import com.moonsky.processing.holder.Holders;

import javax.lang.model.element.TypeElement;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public class ClassDeclared extends BaseDeclarable {

    /**
     * 所有属性: 包含 setter、getter 方法的属性
     */
    private final Map<String, PropertyDeclared> properties = new LinkedHashMap<>();
    /**
     * 所有非 properties 成员字段
     */
    private final Map<String, FieldDeclared> memberFieldsMap = new LinkedHashMap<>();
    /**
     * 所有静态字段
     */
    private final Map<String, FieldDeclared> staticFieldsMap = new LinkedHashMap<>();
    /**
     * 所有构造器
     */
    private final Map<String, ConstructorDeclared> constructorsMap = new LinkedHashMap<>();
    /**
     * 所有非 properties 静态方法
     */
    private final Map<String, MethodDeclared> staticMethodsMap = new LinkedHashMap<>();
    /**
     * 所有非 properties 成员方法
     */
    private final Map<String, MethodDeclared> memberMethodsMap = new LinkedHashMap<>();

    ClassDeclared(Holders holders, TypeElement typeElement) {
        super(holders, typeElement, typeElement, new GenericsMap(typeElement));
    }

    public static ClassDeclared from(Holders holders, TypeElement typeElement) {
        return new ClassHelper(holders, typeElement).doParseTypeDeclared();
    }

    void setProperties(Map<String, PropertyDeclared> properties) {
        putAllIfEmpty(this.properties, properties);
    }

    void setStaticFieldsMap(Map<String, FieldDeclared> staticFieldsMap) {
        putAllIfEmpty(this.staticFieldsMap, staticFieldsMap);
    }

    void setStaticMethodsMap(Map<String, MethodDeclared> staticMethodsMap) {
        putAllIfEmpty(this.staticMethodsMap, staticMethodsMap);
    }

    void setMemberMethodsMap(Map<String, MethodDeclared> memberMethodsMap) {
        putAllIfEmpty(this.memberMethodsMap, memberMethodsMap);
    }

    void setConstructorsMap(Map<String, ConstructorDeclared> constructorsMap) {
        putAllIfEmpty(this.constructorsMap, constructorsMap);
    }

    private static <K, V, M extends Map<K, V>> void putAllIfEmpty(M target, M source) {
        if (target.isEmpty()) {
            target.putAll(source);
        }
    }
}
