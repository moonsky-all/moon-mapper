package com.moonsky.mapper.processing.util;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Set;

/**
 * @author benshaoye
 */
public enum Test2 {
    ;

    public static boolean isTypeKind(TypeMirror elem, TypeKind kind) { return elem != null && elem.getKind() == kind; }

    public static boolean isElemKind(Element elem, ElementKind kind) { return elem != null && elem.getKind() == kind; }

    public static boolean isEnum(Element elem) { return isElemKind(elem, ElementKind.ENUM); }

    public static boolean isEnum(String enumClass) {
        return enumClass != null && isEnum(Processing2.getUtils().getTypeElement(enumClass));
    }

    public static boolean isAll(Element elem, Modifier modifier, Modifier... modifiers) {
        if (elem == null) {
            return false;
        }
        Set<Modifier> modifierSet = elem.getModifiers();
        if (modifierSet.contains(modifier)) {
            if (modifiers == null) {
                return true;
            }
            for (Modifier m : modifiers) {
                if (!modifierSet.contains(m)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isAny(Element elem, Modifier modifier, Modifier... modifiers) {
        if (elem == null) {
            return false;
        }
        Set<Modifier> modifierSet = elem.getModifiers();
        if (modifierSet.contains(modifier)) {
            return true;
        } else if (modifiers != null) {
            for (Modifier m : modifiers) {
                if (modifierSet.contains(m)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNotAny(Element elem, Modifier modifier, Modifier... modifiers) {
        return !isAny(elem, modifier, modifiers);
    }

    public static boolean isPublic(Element elem) { return isAny(elem, Modifier.PUBLIC); }

    public static boolean isMember(Element elem) { return isNotAny(elem, Modifier.STATIC); }

    public static boolean isMethod(Element elem) {
        return elem instanceof ExecutableElement && isElemKind(elem, ElementKind.METHOD);
    }

    public static boolean isField(Element elem) {
        return elem instanceof VariableElement && isElemKind(elem, ElementKind.FIELD);
    }

    public static boolean isMemberField(Element elem) { return isMember(elem) && isField(elem); }
}
