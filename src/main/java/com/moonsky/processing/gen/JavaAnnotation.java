package com.moonsky.processing.gen;

import com.moonsky.processing.util.Element2;
import com.moonsky.processing.util.Importer;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import static com.moonsky.processing.util.Const2.EMPTY_STRINGS;

/**
 * @author benshaoye
 */
public class JavaAnnotation extends AbstractImportable implements Addable {

    private final Map<String, JavaAnnotationValue> valuesMap = new TreeMap<>();

    private final JavaElementEnum elementEnum;

    private final String annotationName;

    public JavaAnnotation(Importer importer, JavaElementEnum elementEnum, String annotationName) {
        super(importer);
        this.elementEnum = elementEnum;
        this.annotationName = annotationName;
    }

    public JavaAnnotation classOf(String method, Class<?> classValue, Class<?>... classValues) {
        return classOf(method, classValue.getCanonicalName(), Element2.toClassnames(classValues));
    }

    public JavaAnnotation classOf(String method, String classname, String... classnames) {
        putVal(method, JavaAnnotationValueType.CLASS, classname, classnames);
        return this;
    }

    public JavaAnnotation stringOf(String method, Object value, Object... values) {
        putVal(method,
            JavaAnnotationValueType.STRING,
            String.valueOf(value),
            values == null ? EMPTY_STRINGS : Arrays.stream(values).map(String::valueOf).toArray(String[]::new));
        return this;
    }

    public JavaAnnotation intOf(String method, int value, int... values) {
        String[] valuesStringify = values == null ? EMPTY_STRINGS : Arrays.stream(values)
            .boxed()
            .map(String::valueOf)
            .toArray(String[]::new);
        putVal(method, JavaAnnotationValueType.PRIMITIVE, String.valueOf(value), valuesStringify);
        return this;
    }

    public JavaAnnotation longOf(String method, long value, long... values) {
        String[] valuesStringify = values == null ? EMPTY_STRINGS : Arrays.stream(values)
            .boxed()
            .map(String::valueOf)
            .toArray(String[]::new);
        putVal(method, JavaAnnotationValueType.PRIMITIVE, String.valueOf(value), valuesStringify);
        return this;
    }

    public JavaAnnotation doubleOf(String method, double value, double... values) {
        String[] valuesStringify = values == null ? EMPTY_STRINGS : Arrays.stream(values)
            .boxed()
            .map(String::valueOf)
            .toArray(String[]::new);
        putVal(method, JavaAnnotationValueType.PRIMITIVE, String.valueOf(value), valuesStringify);
        return this;
    }

    public JavaAnnotation booleanOf(String method, boolean value) {
        putVal(method, JavaAnnotationValueType.PRIMITIVE, String.valueOf(value));
        return this;
    }

    public JavaAnnotation trueOf(String method) { return booleanOf(method, true); }

    public JavaAnnotation falseOf(String method) { return booleanOf(method, false); }

    public JavaAnnotationValue with(String method) {
        JavaAnnotationValue value = getValuesMap().get(method);
        return value == null ? putVal(method, null, null) : value;
    }

    private JavaAnnotationValue putVal(String method, JavaAnnotationValueType type, String value, String... values) {
        JavaAnnotationValue val = new JavaAnnotationValue(getImporter(), method, type, value, values);
        getValuesMap().put(method, val);
        return val;
    }

    public Map<String, JavaAnnotationValue> getValuesMap() { return valuesMap; }

    @Override
    public void add(JavaAddr addr) {
        addr.newLine("@").add(onImported(annotationName));
        if (getValuesMap().isEmpty()) {
            return;
        }
        addr.add("(").open();
        for (JavaAnnotationValue value : getValuesMap().values()) {
            if (value.isAvailable()) {
                value.add(addr);
                addr.add(',');
            }
        }
        // 删除最后一个逗号
        addr.deleteLastChar().close();
        addr.newLine(')');
    }
}
