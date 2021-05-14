package com.moonsky.processing.declared;

import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.Element2;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

/**
 * @author benshaoye
 */
public class FieldDeclared extends BaseDeclarable {

    /**
     * 字段声明
     */
    private final VariableElement fieldElement;
    private final Set<Modifier> modifiers;
    private final String name;
    private final String type;
    /**
     * 字段声明类型
     */
    private final String declaredType;

    protected FieldDeclared(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        GenericsMap thisGenericsMap,
        VariableElement fieldElement
    ) {
        super(holders, thisElement, enclosingElement, thisGenericsMap);
        this.fieldElement = fieldElement;
        this.name = Element2.getSimpleName(fieldElement);
        this.modifiers = unmodifiableSet(fieldElement.getModifiers());
        String declaredType = fieldElement.asType().toString();
        this.type = getMappedActual(declaredType);
        this.declaredType = declaredType;
    }

    public <A extends Annotation> A getFieldAnnotation(Class<A> annotationClass) {
        return fieldElement.getAnnotation(annotationClass);
    }

    public boolean isStatic() { return hasModifierOf(Modifier.STATIC); }

    public boolean isTransient() { return hasModifierOf(Modifier.TRANSIENT); }

    public boolean hasModifierOf(Modifier modifier) {
        return getModifiers().contains(modifier);
    }

    public String getType() { return type; }

    public VariableElement getFieldElement() { return fieldElement; }

    public Set<Modifier> getModifiers() { return modifiers; }

    public String getName() { return name; }

    public String getDeclaredType() { return declaredType; }
}
