package com.moonsky.processing.definition;

import com.moonsky.processing.util.Element2;
import com.moonsky.processing.util.Generic2;
import com.moonsky.processing.util.Test2;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author benshaoye
 */
public class Conversion {

    private final String convertClass;
    private final String convertMethodName;
    private final String fromClassname;
    private final String toClassname;
    private final boolean fromUsable;
    private final boolean toUsable;

    Conversion(String convertClass, ExecutableElement method, TypeMirror fromMirror, TypeMirror toMirror) {
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
        return Conversions.forConversionKey(this.fromClassname, this.toClassname);
    }
}
