package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;
import com.moonsky.processing.util.Test2;

import javax.lang.model.element.TypeElement;
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

    public void valueOf(Object value) { withValue(String.valueOf(value)); }

    public void valueOfClassRef(Class<?> klass) { valueOf(onImported(klass) + ".class"); }

    public void valueOfClassRef(TypeElement klass) { valueOf(onImported(klass) + ".class"); }

    public void valueOfClassRef(String classname) { valueOf(onImported(classname) + ".class"); }

    public void valueOfStringify(String value) { valueOf('"' + value + '"'); }

    public void valueOfFormatted(String template, Object... values) {
        valueOf(String2.format(template, values));
    }

    public void valueOfTypeFormatted(String template, Object... values) {
        valueOf(TypeFormatter2.with(template, values));
    }

    /**
     * 指定类静态字段引用
     *
     * @param classname
     * @param staticMemberName
     */
    public void valueOfStaticRef(String classname, String staticMemberName) {
        valueOf(String2.format("{}.{}", onImported(classname), staticMemberName));
    }

    /**
     * 指定枚举指定枚举值引用
     *
     * @param enumClass
     * @param enumConstName
     */
    public void valueOfEnumConstRef(String enumClass, String enumConstName) {
        valueOfStaticRef(enumClass, enumConstName);
    }

    /**
     * 指定枚举类指定枚举值的指定字段引用
     *
     * @param enumClass
     * @param enumConstName
     * @param memberRef
     */
    public void valueOfEnumMemberRef(String enumClass, String enumConstName, String memberRef) {
        if (Objects.equals(thisClass, enumClass)) {
            valueOfThisEnumMemberRef(enumConstName, memberRef);
        } else {
            valueOf(String2.format("{}.{}.{}", onImported(enumClass), enumConstName, memberRef));
        }
    }

    /**
     * 当前类如果是枚举，这个引用本类枚举的某个成员字段
     *
     * @param enumValue
     * @param memberRef
     */
    public void valueOfThisEnumMemberRef(String enumValue, String memberRef) {
        withValue(String2.format("{}.{}", enumValue, memberRef));
    }

    void addFieldValue(JavaAddr addr) { addr.add(" = ").add(value); }
}
