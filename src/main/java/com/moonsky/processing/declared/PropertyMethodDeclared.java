package com.moonsky.processing.declared;

import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.Const2;
import com.moonsky.processing.util.String2;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import static com.moonsky.processing.declared.ExecutionEnum.GETTER_METHOD;
import static com.moonsky.processing.declared.ExecutionEnum.SETTER_METHOD;
import static com.moonsky.processing.declared.PropertyMethodEnum.GETTER;
import static com.moonsky.processing.declared.PropertyMethodEnum.SETTER;

/**
 * @author benshaoye
 */
public class PropertyMethodDeclared extends MethodDeclared {

    private final Supplier<FieldDeclared> fieldGetter;
    private final TypeDeclared propertyTypeDeclared;

    protected PropertyMethodDeclared(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        GenericsMap superGenericsMap,
        ExecutableElement executableElement,
        Supplier<FieldDeclared> fieldGetter,
        String methodName
    ) {
        super(holders,
            thisElement,
            enclosingElement,
            superGenericsMap,
            executableElement,
            methodName.startsWith(Const2.SET) ? SETTER_METHOD : GETTER_METHOD);
        this.propertyTypeDeclared = toPropertyTypeDeclared();
        this.fieldGetter = fieldGetter;
    }

    private TypeDeclared toPropertyTypeDeclared() {
        if (getExecutionEnum() == GETTER_METHOD) {
            return getReturnTypeDeclared();
        } else {
            return getParameterAt(0).toTypeDeclared();
        }
    }

    private FieldDeclared getFieldDeclared() { return fieldGetter.get(); }

    public <A extends Annotation> A[] getFieldAnnotations(Class<A> annotationType) {
        FieldDeclared fieldDeclared = this.getFieldDeclared();
        if (fieldDeclared == null) {
            @SuppressWarnings("all")
            A[] array = (A[]) Array.newInstance(annotationType, 0);
            return array;
        }
        VariableElement fieldElement = fieldDeclared.getFieldElement();
        if (fieldElement == null) {
            @SuppressWarnings("all")
            A[] array = (A[]) Array.newInstance(annotationType, 0);
            return array;
        }
        return fieldElement.getAnnotationsByType(annotationType);
    }

    public String getPropertyActualType() { return getPropertyTypeDeclared().getActual(); }

    public TypeDeclared getPropertyTypeDeclared() { return propertyTypeDeclared; }

    public static PropertyMethodDeclared ofLombokSetterGenerated(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        GenericsMap superGenericsMap,
        FieldDeclared fieldDeclared
    ) {
        return LombokPropertyMethodDeclared.of(holders,
            thisElement,
            enclosingElement,
            superGenericsMap,
            fieldDeclared,
            SETTER);
    }

    public static PropertyMethodDeclared ofLombokGetterGenerated(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        GenericsMap superGenericsMap,
        FieldDeclared fieldDeclared
    ) {
        return LombokPropertyMethodDeclared.of(holders,
            thisElement,
            enclosingElement,
            superGenericsMap,
            fieldDeclared,
            GETTER);
    }

    private static class LombokPropertyMethodDeclared extends PropertyMethodDeclared {

        protected LombokPropertyMethodDeclared(
            Holders holders,
            TypeElement thisElement,
            TypeElement enclosingElement,
            GenericsMap superGenericsMap,
            ExecutableElement executableElement,
            Supplier<FieldDeclared> fieldGetter
        ) {
            super(holders,
                thisElement,
                enclosingElement,
                superGenericsMap,
                executableElement,
                fieldGetter,
                executableElement.getSimpleName().toString());
        }

        static PropertyMethodDeclared of(
            Holders holders,
            TypeElement thisElement,
            TypeElement enclosingElement,
            GenericsMap superGenericsMap,
            FieldDeclared fieldDeclared,
            PropertyMethodEnum methodEnum
        ) {
            return new LombokPropertyMethodDeclared(holders,
                thisElement,
                enclosingElement,
                superGenericsMap,
                new LombokFieldExecutableElement(holders, fieldDeclared, methodEnum),
                () -> fieldDeclared);
        }
    }

    private static class LombokFieldExecutableElement implements ExecutableElement {

        private final FieldDeclared field;
        private final PropertyMethodEnum methodEnum;
        private final MethodName methodName;
        private final TypeMirror returnType;

        private LombokFieldExecutableElement(Holders holders, FieldDeclared field, PropertyMethodEnum methodEnum) {
            String methodName;
            TypeMirror returnType;
            if (methodEnum == GETTER) {
                methodName = String2.toGetterName(field.getName(), field.getType());
                returnType = field.getFieldElement().asType();
            } else {
                methodName = String2.toSetterName(field.getName());
                returnType = holders.getUtils().getTypeElement("void").asType();
            }
            this.methodName = new MethodName(methodName);
            this.methodEnum = methodEnum;
            this.returnType = returnType;
            this.field = field;
        }

        private VariableElement getField() { return field.getFieldElement(); }

        @Override
        public List<? extends TypeParameterElement> getTypeParameters() {
            return Collections.emptyList();
        }

        @Override
        public TypeMirror getReturnType() { return getField().asType(); }

        @Override
        public List<? extends VariableElement> getParameters() {
            if (methodEnum == SETTER) {
                return Collections.singletonList(getField());
            } else {
                return Collections.emptyList();
            }
        }

        @Override
        public TypeMirror getReceiverType() {
            throw unsupported();
        }

        @Override
        public boolean isVarArgs() { return false; }

        @Override
        public boolean isDefault() { return false; }

        @Override
        public List<? extends TypeMirror> getThrownTypes() {
            return Collections.emptyList();
        }

        @Override
        public AnnotationValue getDefaultValue() { return null; }

        @Override
        public TypeMirror asType() { return returnType; }

        @Override
        public ElementKind getKind() { return ElementKind.METHOD; }

        @Override
        public Set<Modifier> getModifiers() {
            return Collections.singleton(Modifier.PUBLIC);
        }

        @Override
        public Name getSimpleName() { return methodName; }

        @Override
        public Element getEnclosingElement() {
            return field.getEnclosingElement();
        }

        @Override
        public List<? extends Element> getEnclosedElements() {
            return Collections.emptyList();
        }

        @Override
        public List<? extends AnnotationMirror> getAnnotationMirrors() {
            return Collections.emptyList();
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
            return null;
        }

        @Override
        public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
            return (A[]) Array.newInstance(annotationType, 0);
        }

        @Override
        public <R, P> R accept(ElementVisitor<R, P> v, P p) { throw unsupported(); }
    }

    private static class MethodName implements Name {

        private final String name;

        private MethodName(String name) {this.name = name;}

        @Override
        public boolean contentEquals(CharSequence cs) {
            return Objects.equals(name, cs == null ? null : cs.toString());
        }

        @Override
        public int length() { return name == null ? 0 : name.length(); }

        @Override
        public char charAt(int index) { return name.charAt(index); }

        @Override
        public CharSequence subSequence(int start, int end) { return name.substring(start, end); }

        @Override
        public String toString() { return name; }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }
            MethodName that = (MethodName) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() { return Objects.hash(name); }
    }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException();
    }
}
