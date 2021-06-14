package com.moonsky.processing.util;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.MonthDay;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePeriod;
import org.joda.time.YearMonth;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static com.moonsky.processing.util.Const2.*;
import static javax.lang.model.element.ElementKind.CONSTRUCTOR;

/**
 * @author benshaoye
 */
public enum Test2 {
    ;

    private final static Set<String> PRIMITIVE_NUMS = new HashSet<>();
    private final static Set<String> WRAPPED_NUMS_TYPE = new HashSet<>();
    private final static Set<String> BASIC_TYPES = new HashSet<>();

    static {
        Class<?>[] primitives = {byte.class, short.class, int.class, long.class, float.class, double.class};
        Class<?>[] wrappedTypes = {Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class};
        @SuppressWarnings("all")
        Class<?>[] basicTypes = {
            Object.class, String.class, StringBuilder.class, StringBuffer.class, CharSequence.class,//
            void.class, char.class, boolean.class, Character.class, Boolean.class, Number.class
        };
        Collect2.addAll(Class::getCanonicalName, PRIMITIVE_NUMS, primitives);
        Collect2.addAll(Class::getCanonicalName, WRAPPED_NUMS_TYPE, wrappedTypes);
        Collect2.addAll(Class::getCanonicalName, BASIC_TYPES, wrappedTypes);
        Collect2.addAll(Class::getCanonicalName, BASIC_TYPES, basicTypes);
    }

    public static boolean isTypeKind(TypeMirror elem, TypeKind kind) {
        return elem != null && elem.getKind() == kind;
    }

    public static boolean isElemKind(Element elem, ElementKind kind) {
        return elem != null && elem.getKind() == kind;
    }

    /**
     * 是否是枚举类
     *
     * @param elem 检测元素
     *
     * @return 如果待检测元素是枚举类，返回 true，否则返回 false
     */
    public static boolean isEnumClass(Element elem) {
        return isElemKind(elem, ElementKind.ENUM);
    }

    public static boolean isEnumClass(String enumClass) {
        return enumClass != null && isEnumClass(Processing2.getUtils().getTypeElement(enumClass));
    }

    public static boolean isEnumValue(Element elem) {
        return isElemKind(elem, ElementKind.ENUM_CONSTANT);
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
        return elem != null && hasAny(elem.getModifiers(), modifier, modifiers);
    }

    public static <T> boolean hasAny(Collection<T> collect, T v, T... values) {
        if (collect == null) {
            return false;
        }
        if (collect.contains(v)) {
            return true;
        } else if (values != null) {
            for (T m : values) {
                if (collect.contains(m)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNotAny(Element elem, Modifier modifier, Modifier... modifiers) {
        return !isAny(elem, modifier, modifiers);
    }

    public static boolean isPublic(Element elem) {
        return isAny(elem, Modifier.PUBLIC);
    }

    public static boolean isMember(Element elem) {
        return isNotAny(elem, Modifier.STATIC);
    }

    public static boolean isMethod(Element elem) {
        return elem instanceof ExecutableElement && isElemKind(elem, ElementKind.METHOD);
    }

    public static boolean isField(Element elem) {
        return elem instanceof VariableElement && isElemKind(elem, ElementKind.FIELD);
    }

    public static boolean isMemberField(Element elem) {
        return isMember(elem) && isField(elem);
    }

    public static boolean isTypeofAny(String actual, Class<?> expected, Class<?>... classes) {
        if (isTypeof(expected.getCanonicalName(), actual)) {
            return true;
        }
        if (classes != null) {
            for (Class<?> klass : classes) {
                if (isTypeof(klass.getCanonicalName(), actual)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isTypeof(String actual, Class<?> expected) {
        return isTypeof(expected.getCanonicalName(), actual);
    }

    public static boolean isTypeof(String actual, String expected) {
        return expected.equals(actual);
    }

    public static boolean hasGeneric(String fulledClassname) {
        return fulledClassname.contains("<");
    }

    public static boolean isIntValue(String value) {
        return isValidOnTrimmed(value, Integer::parseInt);
    }

    public static boolean isBasicNumberValue(String type, String value) {
        switch (type) {
            case "byte":
            case "java.lang.Byte":
                return isValidOnTrimmed(value, Byte::valueOf);
            case "short":
            case "java.lang.Short":
                return isValidOnTrimmed(value, Short::valueOf);
            case "int":
            case "java.lang.Integer":
                return isValidOnTrimmed(value, Integer::valueOf);
            case "long":
            case "java.lang.Long":
                return isValidOnTrimmed(value, Long::valueOf);
            case "float":
            case "java.lang.Float":
                return isValidOnTrimmed(value, Float::valueOf);
            case "double":
            case "java.lang.Double":
                return isValidOnTrimmed(value, Double::valueOf);
            default:
                return false;
        }
    }

    public static boolean isValidOnTrimmed(String value, Consumer<String> consumer) {
        try {
            consumer.accept(value.trim());
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public static boolean isWrappedNumberClass(Class<?> klass) {
        return klass != null && isWrappedNumberClass(klass.getCanonicalName());
    }

    public static boolean isWrappedNumberClass(String classname) {
        return WRAPPED_NUMS_TYPE.contains(classname);
    }

    public static boolean isBasicTypeClass(Class<?> type) {
        return isBasicTypeClass(getName(type));
    }

    public static boolean isBasicTypeClass(String type) {
        return BASIC_TYPES.contains(type) || isPrimitiveClass(type);
    }

    public static boolean isPrimitiveClass(Class<?> type) {
        return isPrimitiveClass(getName(type));
    }

    public static boolean isPrimitiveClass(String type) {
        return isPrimitiveNumberClass(type) || isPrimitiveBoolClass(type) || isPrimitiveCharClass(type);
    }

    public static boolean isPrimitiveNumberClass(Class<?> type) {
        return isPrimitiveNumberClass(getName(type));
    }

    public static boolean isPrimitiveNumberClass(String type) {
        return PRIMITIVE_NUMS.contains(type);
    }

    public static boolean isPrimitiveBoolClass(TypeMirror type) {
        return isTypeKind(type, TypeKind.BOOLEAN);
    }

    public static boolean isPrimitiveBoolClass(Class<?> type) {
        return isPrimitiveBoolClass(getName(type));
    }

    public static boolean isPrimitiveBoolClass(String type) {
        return "boolean".contains(type);
    }

    public static boolean isPrimitiveCharClass(Class<?> type) {
        return isPrimitiveCharClass(getName(type));
    }

    public static boolean isPrimitiveCharClass(String type) {
        return "char".contains(type);
    }

    public static boolean isPrimitiveNumberSubtypeOf(String thisType, String thatType) {
        String primitives = ",byte,short,int,long,float,double";
        int thisIdx = primitives.indexOf(thisType);
        int thatIdx = primitives.indexOf(thatType);
        return thisIdx > 0 && thisIdx < thatIdx;
    }

    private static String getName(Class<?> cls) { return cls.getCanonicalName(); }

    public static boolean isVoid(String classname) { return "void".equals(classname); }

    public static boolean isVoid(Class<?> klass) { return klass == void.class; }

    public static boolean isPublicMemberMethod(Element elem) {
        return isMethod(elem) && isMember(elem) && isPublic(elem);
    }

    public static boolean isSetterMethod(Element elem) {
        if (isPublicMemberMethod(elem)) {
            ExecutableElement exe = (ExecutableElement) elem;
            String name = exe.getSimpleName().toString();
            boolean maybeSet = name.length() > 3 && name.startsWith(SET);
            maybeSet = maybeSet && exe.getParameters().size() == 1;
            // maybeSet = maybeSet && isTypeKind(exe.getReturnType(), TypeKind.VOID);
            return maybeSet;
        }
        return false;
    }

    public static boolean isGetterMethod(Element elem) {
        if (isPublicMemberMethod(elem)) {
            ExecutableElement exe = (ExecutableElement) elem;
            String name = exe.getSimpleName().toString();
            boolean maybeGet = exe.getParameters().isEmpty();
            if (name.startsWith(GET)) {
                return maybeGet && name.length() > 3 && !name.equals(GET_CLASS);
            } else if (name.startsWith(IS)) {
                return maybeGet && name.length() > 2 && isTypeKind(exe.getReturnType(), TypeKind.BOOLEAN);
            }
        }
        return false;
    }

    public static boolean isConstructor(Element elem) { return isElemKind(elem, CONSTRUCTOR); }

    public static boolean hasLombokGetter(VariableElement field) {
        if (Import2.LOMBOK) {
            if (field == null) {
                return false;
            }
            Getter getter = field.getAnnotation(Getter.class);
            if (getter != null) {
                return getter.value() == AccessLevel.PUBLIC;
            } else {
                Element element = field.getEnclosingElement();
                return element.getAnnotation(Data.class) != null;
            }
        }
        return false;
    }

    public static boolean hasLombokSetter(VariableElement field) {
        if (Import2.LOMBOK) {
            if (field == null) {
                return false;
            }
            Setter setter = field.getAnnotation(Setter.class);
            if (setter != null) {
                return setter.value() == AccessLevel.PUBLIC;
            } else {
                Element element = field.getEnclosingElement();
                return element.getAnnotation(Data.class) != null;
            }
        }
        return false;
    }

    public static boolean isSubtypeOf(String actualType, Class<?> superclass) {
        return isSubtypeOf(actualType, superclass.getCanonicalName());
    }

    public static boolean isSubtypeOf(String actualType, String superClass) {
        if (actualType == null || superClass == null) {
            return false;
        }
        Elements utils = Processing2.getUtils();
        return isSubtypeOf(utils.getTypeElement(actualType), utils.getTypeElement(superClass));
    }

    public static boolean isSubtypeOf(TypeElement thisElem, TypeElement superElem) {
        Types types = Processing2.getTypes();
        return thisElem != null && superElem != null && types.isSubtype(thisElem.asType(), superElem.asType());
    }

    public static boolean isImportedAndJodaDateClass(String actualType) {
        if (Import2.JODA_TIME_2X && isTypeofAny(actualType, YearMonth.class, MonthDay.class)) {
            return true;
        }
        if (Import2.JODA_TIME_1X0) {
            return isSubtypeOf(actualType, ReadableInstant.class) || isSubtypeOf(actualType, ReadablePeriod.class);
        }
        return false;
    }
}
