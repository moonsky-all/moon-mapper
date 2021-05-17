package com.moonsky.processing.generate;

import com.moonsky.processing.util.String2;

import javax.lang.model.element.TypeElement;

/**
 * @author benshaoye
 */
public class JavaElemFieldValue extends AbstractImportable {

    private final JavaElemField field;
    private final String fieldType;
    private boolean available;
    private String value;

    public JavaElemFieldValue(JavaElemField field, String fieldType) {
        super(field.getImporter());
        this.fieldType = fieldType;
        this.field = field;
    }

    public JavaElemField end() { return field; }

    public JavaElemFieldValue clear() {
        this.value = null;
        this.available = false;
        return this;
    }

    public boolean isAvailable() { return available; }

    private JavaElemFieldValue withValue(String value) {
        this.available = true;
        this.value = value;
        return this;
    }

    public JavaElemFieldValue valueOf(Object value) {return withValue(String.valueOf(value)); }

    public JavaElemFieldValue valueOfClassRef(Class<?> klass) { return valueOf(onImported(klass) + ".class"); }

    public JavaElemFieldValue valueOfClassRef(TypeElement klass) {return valueOf(onImported(klass) + ".class"); }

    public JavaElemFieldValue valueOfClassRef(String classname) {return valueOf(onImported(classname) + ".class"); }

    public JavaElemFieldValue valueOfStringify(String value) { return valueOf('"' + value + '"'); }

    public JavaElemFieldValue valueOfFormatted(String template, Object... values) {
        return valueOf(String2.formatImported(getImporter(), template, values));
    }

    public JavaElemFieldValue valueOfTypeFormatted(String template, Object... values) {
        return valueOf(TypeFormatter2.with(template, values));
    }

    /**
     * 指定类静态字段引用
     *
     * @param classname
     * @param staticMemberName
     */
    public JavaElemFieldValue valueOfStaticRef(String classname, String staticMemberName) {
        return valueOf(String2.format("{}.{}", onImported(classname), staticMemberName));
    }

    /**
     * 指定枚举指定枚举值引用
     *
     * @param enumClass
     * @param enumConstName
     */
    public JavaElemFieldValue valueOfEnumConstRef(String enumClass, String enumConstName) {
        return valueOfStaticRef(enumClass, enumConstName);
    }

    /**
     * 指定枚举类指定枚举值的指定字段引用
     *
     * @param enumClass
     * @param enumConstName
     * @param memberRef
     */
    public JavaElemFieldValue valueOfEnumMemberRef(String enumClass, String enumConstName, String memberRef) {
        return valueOf(String2.format("{}.{}.{}", onImported(enumClass), enumConstName, memberRef));
    }

    /**
     * 当前类如果是枚举，这个引用本类枚举的某个成员字段
     *
     * @param enumValue
     * @param memberRef
     */
    public JavaElemFieldValue valueOfThisEnumMemberRef(String enumValue, String memberRef) {
        return withValue(String2.format("{}.{}", enumValue, memberRef));
    }

    void addFieldValue(JavaAddr addr) {
        if (isAvailable()) {
            addr.add(" = ").add(value);
        }
    }
}
