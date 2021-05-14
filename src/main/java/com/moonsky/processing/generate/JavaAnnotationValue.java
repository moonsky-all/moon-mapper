package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author benshaoye
 */
public class JavaAnnotationValue extends AbstractImportable implements Addable {

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

    private List<String> getValues() { return values; }

    public JavaAnnotationValueType getType() { return type; }

    public String getMethod() { return method; }

    @Override
    public void add(JavaAddr addr) {
        List<String> values = getValues();
        if (values.isEmpty()) {
            addr.add("{}");
        } else if (values.size() == 1) {
            addr.add(toValue(values.get(0)));
        } else if (addr.isOverLength(values.size() * 16 + 2)) {
            addr.add("{").open();
            for (String value : values) {
                addr.newLine(toValue(value)).add(",");
            }
            addr.deleteLastChar().close();
            addr.newLine("}");
        } else {
            addr.add("{").add(values.stream().map(this::toValue).collect(Collectors.joining(", "))).add("}");
        }
    }

    private String toValue(String value) {
        switch (getType()) {
            case STRING:
                return with("\"", value, "\"");
            case ENUM:
            case PRIMITIVE:
                return with(value);
            case CLASS:
                return with(onImported(value), ".class");
            default:
                throw new IllegalStateException();
        }
    }

    private static String with(String... values) {
        StringBuilder builder = new StringBuilder();
        for (String value : values) {
            builder.append(value);
        }
        return builder.toString();
    }
}
