package com.moonsky.processing.definition;

import com.moonsky.mapper.util.DateConvert;
import com.moonsky.mapper.util.Joda1xConvert;
import com.moonsky.mapper.util.Joda2xConvert;
import com.moonsky.processing.util.*;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author benshaoye
 */
public class Conversion {

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
                } else if (Imported.JODA_TIME_1X) {
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
        final String convertClass = Element2.getQualifiedName(convertElem);
        List<? extends Element> elements = convertElem.getEnclosedElements();
        for (Element element : elements) {
            if (!Test2.isMethod(element)) {
                continue;
            }
            if (!Test2.isAll(element, Modifier.STATIC, Modifier.PUBLIC)) {
                continue;
            }
            ExecutableElement method = (ExecutableElement) element;
            List<? extends VariableElement> parameters = method.getParameters();
            if (parameters == null || parameters.size() != 1) {
                continue;
            }
            TypeMirror fromMirror = parameters.get(0).asType();
            TypeMirror toMirror = method.getReturnType();
            Conversion conversion = new Conversion(convertClass, method, fromMirror, toMirror);
            if (conversion.isUsable()) {
                conversions.putIfAbsent(conversion.getUniqueKey(), conversion);
            }
        }
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
    public static Conversion findMatchedConversion(String fromClassname, String toClassname) {
        return loadConversions().get(forConversionKey(fromClassname, toClassname));
    }

    private final String convertClass;
    private final String convertMethodName;
    private final String fromClassname;
    private final String toClassname;
    private final boolean fromUsable;
    private final boolean toUsable;

    public Conversion(String convertClass, ExecutableElement method, TypeMirror fromMirror, TypeMirror toMirror) {
        this.fromClassname = fromMirror.toString();
        this.toClassname = toMirror.toString();
        this.convertClass = convertClass;
        this.convertMethodName = method.getSimpleName().toString();

        String simpleToClassname = Generic2.typeSimplify(this.toClassname);
        String simpleFromClassname = Generic2.typeSimplify(this.fromClassname);
        TypeElement fromElement = Element2.getTypeElement(simpleFromClassname);
        TypeElement toElement = Element2.getTypeElement(simpleToClassname);
        this.fromUsable = fromElement != null || Test2.isPrimitiveClass(fromClassname);
        this.toUsable = toElement != null || Test2.isPrimitiveClass(toClassname);
    }

    public String getConvertClass() { return convertClass; }

    public String getConvertMethodName() { return convertMethodName; }

    public boolean isUsable() { return fromUsable && toUsable; }

    public String getUniqueKey() {
        return forConversionKey(this.fromClassname, this.toClassname);
    }
}
