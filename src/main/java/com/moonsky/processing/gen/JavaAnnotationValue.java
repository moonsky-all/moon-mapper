package com.moonsky.processing.gen;

import com.moonsky.processing.util.Const2;
import com.moonsky.processing.util.Importer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author benshaoye
 */
public class JavaAnnotationValue extends AbstractImportable {

    private final String method;
    private final List<String> values = new ArrayList<>();
    private JavaAnnotationValueType type;

    public JavaAnnotationValue(
        Importer importer, String method, JavaAnnotationValueType type, String value, String... values
    ) {
        super(importer);
        this.method = method;
        this.type = type;
        // values
        if (value != null) {
            this.values.add(value);
        }
        if (values != null) {
            this.values.addAll(Arrays.asList(values));
        }
    }

    public JavaAnnotationValue typeOf(JavaAnnotationValueType type) {
        this.type = type;
        return this;
    }

    public JavaAnnotationValue typeOfEnum() { return typeOf(JavaAnnotationValueType.ENUM); }

    public JavaAnnotationValue typeOfClass() { return typeOf(JavaAnnotationValueType.CLASS); }

    public JavaAnnotationValue typeOfString() { return typeOf(JavaAnnotationValueType.STRING); }

    public JavaAnnotationValue typeOfPrimitive() { return typeOf(JavaAnnotationValueType.PRIMITIVE); }

    public JavaAnnotationValue withValues(String... values) {
        if (values != null && values.length > 0) {
            this.values.addAll(Arrays.asList(values));
        } else {
            this.values.clear();
        }
        return this;
    }

    public boolean isAvailable() { return type != null; }
}
