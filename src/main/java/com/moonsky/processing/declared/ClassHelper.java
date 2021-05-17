package com.moonsky.processing.declared;

import com.moonsky.processing.holder.AbstractHolder;
import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.Collect2;
import com.moonsky.processing.util.Element2;
import com.moonsky.processing.util.Test2;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.*;

/**
 * @author benshaoye
 */
final class ClassHelper extends AbstractHolder {

    private final static String TOP_CLASS = Object.class.getCanonicalName();

    private final Map<String, PropertyDeclared> properties = new LinkedHashMap<>();
    private final Map<String, FieldDeclared> staticFieldsMap = new LinkedHashMap<>();
    private final Map<String, MethodDeclared> staticMethodsMap = new LinkedHashMap<>();
    private final Map<String, MethodDeclared> memberMethodsMap = new LinkedHashMap<>();
    private final Map<String, ConstructorDeclared> constructorsMap = new LinkedHashMap<>();
    private final TypeElement typeElement;
    private final ClassDeclared typeDeclared;
    private final Set<String> parsedKeys;
    private final Types types;

    protected ClassHelper(Holders holders, TypeElement typeElement) {
        super(holders);
        this.types = holders.getTypes();
        this.typeDeclared = new ClassDeclared(holders, typeElement);
        this.typeElement = typeElement;
        this.parsedKeys = new HashSet<>();
    }

    public ClassDeclared doParseTypeDeclared() {
        parseRootElements();
        parseSuperElements(typeElement);
        properties.forEach((name, prop) -> prop.onCompleted());
        typeDeclared.setProperties(properties);
        typeDeclared.setStaticFieldsMap(this.staticFieldsMap);
        typeDeclared.setStaticMethodsMap(this.staticMethodsMap);
        typeDeclared.setMemberMethodsMap(this.memberMethodsMap);
        typeDeclared.setConstructorsMap(this.constructorsMap);
        return typeDeclared;
    }

    private PropertyDeclared withPropertyDeclared(String name, TypeElement enclosingElement) {
        PropertyDeclared declared = properties.get(name);
        if (declared == null) {
            declared = new PropertyDeclared(getHolders(), typeElement, enclosingElement, name, typeDeclared);
            properties.put(name, declared);
        }
        return declared;
    }

    private void handleEnclosedElem(Element element, TypeElement parsingElem) {
        String parsingClass = Element2.getQualifiedName(parsingElem);
        String declaredType, actualType;
        if (Test2.isMemberField(element)) {
            String name = element.getSimpleName().toString();
            if (isParsedProperty(name)) {
                return;
            }
            withPropertyDeclared(name, parsingElem).withFieldIfAbsent((VariableElement) element);
        } else if (Test2.isSetterMethod(element)) {
            ExecutableElement elem = (ExecutableElement) element;
            declaredType = getSetterDeclareType(elem);
            actualType = getGenericsMap().getActual(parsingClass, declaredType);
            String name = Element2.toPropertyName(elem);
            if (isParsedSetter(name, actualType)) {
                return;
            }
            withPropertyDeclared(name, parsingElem).withSetterTypedIfAbsent(elem, actualType);
        } else if (Test2.isGetterMethod(element)) {
            ExecutableElement elem = (ExecutableElement) element;
            String name = Element2.toPropertyName(elem);
            if (isParsedGetter(name)) {
                return;
            }
            withPropertyDeclared(name, parsingElem).withGetter(elem);
        } else if (Test2.isConstructor(element)) {
            if (parsingElem == typeElement) {
                ConstructorDeclared constructor = new ConstructorDeclared(getHolders(),
                    typeElement,
                    parsingElem,
                    getGenericsMap(),
                    (ExecutableElement) element);
                constructorsMap.put(constructor.getSignature(), constructor);
            }
        } else if (Test2.isMethod(element)) {
            MethodDeclared method = new MethodDeclared(getHolders(),
                typeElement,
                parsingElem,
                getGenericsMap(),
                (ExecutableElement) element);
            if (Test2.isAny(element, Modifier.STATIC)) {
                staticMethodsMap.putIfAbsent(method.getSignature(), method);
            } else {
                memberMethodsMap.putIfAbsent(method.getSignature(), method);
            }
        } else if (Test2.isField(element)) {
            FieldDeclared field = new FieldDeclared(getHolders(),
                typeElement,
                parsingElem,
                getGenericsMap(),
                (VariableElement) element);
            staticFieldsMap.put(field.getName(), field);
        }
    }

    private void parseRootElements() {
        parseElements(typeElement.getEnclosedElements(), typeElement);
        parseInterfaces(typeElement);
        parsedKeys.addAll(properties.keySet());
    }

    private void parseElements(List<? extends Element> elements, TypeElement parsingElem) {
        for (Element element : Collect2.emptyIfNull(elements)) {
            handleEnclosedElem(element, parsingElem);
        }
    }

    private void parseInterfaces(TypeElement parsingElem) {
        List<? extends TypeMirror> mirrors = parsingElem.getInterfaces();
        if (Collect2.isEmpty(mirrors)) {
            return;
        }
        for (TypeMirror mirror : mirrors) {
            Element element = types.asElement(mirror);
            if (isTopElement(element, mirror)) {
                continue;
            }
            TypeElement interElem = Element2.cast(element);
            parseElements(interElem.getEnclosedElements(), interElem);
            parseSuperElements(interElem);
        }
    }

    private void parseSuperElements(TypeElement justParsedElement) {
        TypeMirror superclass = justParsedElement.getSuperclass();
        Element superElem = types.asElement(superclass);
        if (isTopElement(superElem, superclass)) {
            return;
        }
        TypeElement superElement = Element2.cast(superElem);
        parseElements(superElement.getEnclosedElements(), superElement);
        parseInterfaces(superElement);
        parsedKeys.addAll(properties.keySet());
        parseSuperElements(superElement);
    }

    private GenericsMap getGenericsMap() {
        return getTypeDeclared().getThisGenericsMap();
    }

    private boolean isParsedProperty(String name) { return parsedKeys.contains(name); }

    private boolean isParsedSetter(String name, String setterActualType) {
        return isParsedProperty(name) && properties.get(name).isWritable(setterActualType);
    }

    private boolean isParsedGetter(String name) {
        return isParsedProperty(name) && properties.get(name).isReadable();
    }

    public static String getSetterDeclareType(ExecutableElement elem) {
        return (elem.getParameters().get(0).asType()).toString();
    }

    private ClassDeclared getTypeDeclared() { return typeDeclared; }

    private static boolean isTopElement(Element element, TypeMirror superclass) {
        return element == null || superclass.toString().equals(TOP_CLASS);
    }
}
