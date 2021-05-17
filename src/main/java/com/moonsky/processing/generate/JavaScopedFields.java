package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;

/**
 * @author benshaoye
 */
public class JavaScopedFields extends JavaScopedMembers<JavaElemField> {

    private final JavaScopedMethods scopedMethods;
    private final VarHelper vars;

    public JavaScopedFields(
        Importer importer,
        String classname,
        JavaGenericsList enclosingGenericsList,
        boolean inInterface,
        JavaScopedMethods scopedMethods
    ) {
        super(importer, classname, enclosingGenericsList, inInterface);
        this.scopedMethods = scopedMethods;
        this.vars = VarHelper.of(classname);
    }

    public String nextVar() { return vars.next(getMemberMap().keySet()); }

    public String nextConstVar() { return vars.nextConst(getMemberMap().keySet()); }

    public JavaElemField declareField(String fieldName, String fieldTypeTemplate, Object... types) {
        JavaElemField field = newUsingField(fieldName, fieldTypeTemplate, types);
        getMemberMap().put(fieldName, field);
        return field;
    }

    private JavaElemField newUsingField(String fieldName, String typeTemplate, Object... types) {
        return new JavaElemField(getImporter(),
            scopedMethods,
            TypeFormatter2.with(typeTemplate, types),
            fieldName,
            inInterface());
    }

    @Override
    public JavaScopedFields remove(String fieldName) {
        JavaElemField field = getMemberMap().get(fieldName);
        field.withNonGetterMethod().withNonSetterMethod();
        getMemberMap().remove(fieldName);
        return this;
    }

    void addDeclareFields(JavaAddr addr) {
        if (getMemberMap().isEmpty()) {
            return;
        }
        getMemberMap().forEach((name, field) -> {
            field.addDeclareField(addr);
        });
    }
}
