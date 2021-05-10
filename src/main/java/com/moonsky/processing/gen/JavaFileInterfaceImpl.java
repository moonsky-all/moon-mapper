package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

import javax.lang.model.element.Modifier;

/**
 * @author benshaoye
 */
public class JavaFileInterfaceImpl extends BaseBlockCommentable implements Addable {

    private final String packageName;
    private final String simpleName;
    private final String classname;

    private final JavaGenericsList genericsList;
    private final JavaScopedFields scopedFields;
    private final JavaScopedMethods scopedMethods;
    private final JavaImplementsList implementsList;

    public JavaFileInterfaceImpl(String packageName, String simpleName) {
        this(packageName, simpleName, JavaElementEnum.INTERFACE);
    }

    protected JavaFileInterfaceImpl(
        String packageName, String simpleName, JavaElementEnum elementEnum
    ) {
        super(new Importer(packageName), elementEnum);
        this.packageName = packageName;
        this.simpleName = simpleName;
        this.classname = String.join(".", packageName, simpleName);

        JavaGenericsList genericsList = new JavaGenericsList(getImporter());
        JavaScopedMethods scopedMethods = new JavaScopedMethods(getImporter(), genericsList, inInterface());
        JavaScopedFields scopedFields = new JavaScopedFields(getImporter(), genericsList, inInterface(), scopedMethods);
        JavaImplementsList implementsList = new JavaImplementsList(getImporter());

        this.genericsList = genericsList;
        this.scopedFields = scopedFields;
        this.scopedMethods = scopedMethods;
        this.implementsList = implementsList;

        modifierWith(Modifier.PUBLIC);
        annotationGenerated();
    }

    protected boolean inInterface() { return true; }

    @Override
    protected boolean isAllowModifierWith(Modifier modifier) {
        return modifier == Modifier.PUBLIC;
    }

    @Override
    public void add(JavaAddr addr) {

    }

    @Override
    public String toString() {
        JavaAddr addr = new JavaAddr();
        addr.add("package ").add(packageName).next().next();
        JavaAddr.Mark importMark = addr.mark();

        super.add(addr);
        return "";
    }
}
