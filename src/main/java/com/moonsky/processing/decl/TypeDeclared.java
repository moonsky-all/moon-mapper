package com.moonsky.processing.decl;

import com.moonsky.processing.holder.AbstractHolder;
import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.Generic2;

import javax.lang.model.element.TypeElement;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

/**
 * @author benshaoye
 */
public class TypeDeclared extends AbstractHolder {

    private final TypeElement typeElement;

    private final String thisClassname;

    /**
     * 所有泛型声明，如声明在 java.util.List&lt;T> 上的 T 在实际应用中如果是：
     * <p>
     * List&lt;String> 那么会这样包含:
     * <p>
     * java.util.List#T  ==  java.lang.String
     * java.util.Collection#T  ==  java.lang.String
     * java.util.Iterable#T  ==  java.lang.String
     * ...(所有父类、继承的接口都有其对应的声明和使用时的实际类)
     */
    private final Map<String, GenericDeclared> thisGenericsMap;
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
     * 所有非 properties 方法，包括静态或成员方法
     */
    private final Map<String, MethodDeclared> methodsMap = new LinkedHashMap<>();

    TypeDeclared(Holders holders, TypeElement typeElement) {
        super(holders);
        this.typeElement = typeElement;
        this.thisClassname = typeElement.getQualifiedName().toString();
        this.thisGenericsMap = unmodifiableMap(Generic2.from(typeElement));
    }

    public static TypeDeclared from(Holders holders, TypeElement typeElement) {
        return new TypeHelper(holders, typeElement).doParseTypeDeclared();
    }

    public TypeElement getTypeElement() { return typeElement; }

    public String getThisClassname() { return thisClassname; }

    public Map<String, GenericDeclared> getThisGenericsMap() { return thisGenericsMap; }
}
