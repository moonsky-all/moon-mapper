package com.moonsky.processing.definition;

import com.moonsky.mapper.util.DateConvert;
import com.moonsky.mapper.util.Joda1xConvert;
import com.moonsky.mapper.util.Joda2xConvert;
import com.moonsky.mapper.util.UseForConverter;
import com.moonsky.processing.util.Element2;
import com.moonsky.processing.util.Imported;
import com.moonsky.processing.util.Processing2;
import com.moonsky.processing.util.Test2;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author benshaoye
 */
public enum Conversions {
    ;

    private static final Map<String, Conversion> CONVERSIONS = new ConcurrentHashMap<>();

    public static String forConversionKey(String fromClassname, String toClassname) {
        return fromClassname + ">>#>>" + toClassname;
    }

    private static Map<String, Conversion> loadConversions() {
        if (CONVERSIONS.isEmpty()) {
            TypeElement convertElem;
            Map<String, Conversion> conversions = new HashMap<>();
            try {
                String convertClass;
                if (Imported.JODA_TIME_2X) {
                    convertClass = Joda2xConvert.class.getCanonicalName();
                } else if (Imported.JODA_TIME_1X0) {
                    convertClass = Joda1xConvert.class.getCanonicalName();
                } else {
                    convertClass = DateConvert.class.getCanonicalName();
                }
                convertElem = Element2.getTypeElement(convertClass);
            } catch (Throwable t) {
                return conversions;
            }
            extractConversions(conversions, convertElem);
            CONVERSIONS.putAll(conversions);
            return conversions;
        }
        return CONVERSIONS;
    }

    private static void extractConversions(Map<String, Conversion> conversions, TypeElement convertElem) {
        UseForConverter converterDef = convertElem.getAnnotation(UseForConverter.class);
        loadThisClass(conversions, convertElem, converterDef == null || converterDef.value());
        if (converterDef == null || !converterDef.suppressSuper()) {
            loadSuperclass(conversions, convertElem);
        }
    }

    private static void loadThisClass(
        Map<String, Conversion> conversions, TypeElement convertElem, boolean classConvertible
    ) {
        final String convertClass = Element2.getQualifiedName(convertElem);
        List<? extends Element> elements = convertElem.getEnclosedElements();
        for (Element element : elements) {
            // 构造函数不可用作转换器
            if (!Test2.isMethod(element)) {
                continue;
            }
            // 只有静态公共方法可用作转换器
            if (!Test2.isAll(element, Modifier.STATIC, Modifier.PUBLIC)) {
                continue;
            }
            // 指定了非转换器不会用作转换器
            UseForConverter converterDef = element.getAnnotation(UseForConverter.class);
            if (converterDef == null && !classConvertible) {
                continue;
            } else if (converterDef != null && !converterDef.value()) {
                continue;
            }
            ExecutableElement method = (ExecutableElement) element;
            TypeMirror toMirror = method.getReturnType();
            // 转换器必须有非 void 返回值类型
            if (Test2.isTypeof(toMirror.toString(), void.class)) {
                continue;
            }
            List<? extends VariableElement> parameters = method.getParameters();
            // 转换器只能接受一个入参
            if (parameters == null || parameters.size() != 1) {
                continue;
            }
            TypeMirror fromMirror = parameters.get(0).asType();
            Conversion conversion = new Conversion(convertClass, method, fromMirror, toMirror);
            // 转换器不能有不能加载的元素
            if (conversion.isUsable()) {
                conversions.putIfAbsent(conversion.getUniqueKey(), conversion);
            }
        }
    }

    private static void loadSuperclass(Map<String, Conversion> conversions, TypeElement convertElem) {
        TypeMirror superclass = convertElem.getSuperclass();
        Element superElem = Processing2.getTypes().asElement(superclass);
        if (superElem != null && !superclass.toString().equals(Object.class.getCanonicalName())) {
            extractConversions(conversions, (TypeElement) superElem);
        }
    }

    /**
     * 返回完全匹配的转换器
     *
     * @param fromClassname
     * @param toClassname
     *
     * @return
     */
    public static Conversion findAssignedConversion(String fromClassname, String toClassname) {
        return loadConversions().get(forConversionKey(fromClassname, toClassname));
    }

    public static Conversion findAssignedConversion(Class<?> fromClass, Class<?> toClass) {
        // DateTimeFormat.forPattern("").parse
        return findAssignedConversion(fromClass.getCanonicalName(), toClass.getCanonicalName());
    }
}
