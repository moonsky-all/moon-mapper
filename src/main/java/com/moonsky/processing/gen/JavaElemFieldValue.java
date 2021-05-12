package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;
import com.moonsky.processing.util.Test2;

import java.util.Objects;

/**
 * @author benshaoye
 */
public class JavaElemFieldValue extends AbstractImportable {

    private final String thisClass;
    private final String fieldType;
    private boolean available;
    private String value;

    public JavaElemFieldValue(Importer importer, String thisClass, String fieldType) {
        super(importer);
        this.thisClass = thisClass;
        this.fieldType = fieldType;
    }

    public void clear() {
        this.value = null;
        this.available = false;
    }

    public boolean isAvailable() { return available; }

    private void withValue(String value) {
        this.available = true;
        this.value = value;
    }

    public void valueOf(Object value) {
        this.withValue(String.valueOf(value));
    }

    public void valueOfClassRef(Class<?> klass) { valueOf(onImported(klass) + ".class"); }

    public void valueOfClassRef(String classname) { valueOf(onImported(classname) + ".class"); }

    public void valueOfStringify(String value) { valueOf('"' + value + '"'); }

    public void valueOfFormatted(String template, Object... values) {
        valueOf(String2.format(template, values));
    }

    public void valueOfTypeFormatted(String template, Object... values) {
        valueOf(TypeFormatter2.with(template, values));
    }

    public void valueOfStaticRef(String classname, String staticMemberName) {
        valueOf(String2.format("{}.{}", onImported(classname), staticMemberName));
    }

    public void valueOfEnumConstRef(String enumKlass, String enumConstName) {
        valueOfStaticRef(enumKlass, enumConstName);
    }

    public void valueOfEnumMemberRef(String enumClass, String enumConstName, String memberRef) {
        if (Objects.equals(thisClass, enumClass)) {
            valueOfThisEnumMemberRef(enumConstName, memberRef);
        } else {
            valueOf(String2.format("{}.{}.{}", onImported(enumClass), enumConstName, memberRef));
        }
    }

    public void valueOfThisEnumMemberRef(String enumValue, String memberRef) {
        withValue(String2.format("{}.{}", enumValue, memberRef));
    }

    void addFieldValue(JavaAddr addr) {
        addr.add(" = ").add(value);
    }

    static void addPrimitiveRequiredFieldValue(JavaAddr addr, String importedFieldType) {
        if (Test2.isPrimitiveBool(importedFieldType)) {
            addr.add(" = false");
        } else if (Test2.isPrimitiveNumber(importedFieldType)) {
            addr.add(" = 0");
        } else {
            addr.add(" = null");
        }
    }
}
