package com.moonsky.processing.generate;

import com.moonsky.processing.processor.JavaDefinition;
import com.moonsky.processing.util.Importer;

import javax.lang.model.element.Modifier;

/**
 * @author benshaoye
 */
public class JavaFileInterfaceDefinition extends BaseBlockCommentable implements JavaDefinition {

    protected final static char SPACE_CHAR = ' ';

    private final String packageName;
    private final String simpleName;
    private final String classname;

    private final JavaGenericsList genericsList;
    private final JavaScopedFields scopedFields;
    private final JavaScopedMethods scopedMethods;
    private final JavaImplementsList implementsList;

    public JavaFileInterfaceDefinition(String packageName, String simpleName) {
        this(packageName, simpleName, JavaElementEnum.INTERFACE, JavaImplementsList.Keyword.EXTENDS);
    }

    protected JavaFileInterfaceDefinition(
        String packageName, String simpleName, JavaElementEnum elementEnum, JavaImplementsList.Keyword keyword
    ) {
        super(new Importer(packageName), elementEnum);
        this.packageName = packageName;
        this.simpleName = simpleName;
        this.classname = String.join(".", packageName, simpleName);

        JavaGenericsList genericsList = new JavaGenericsList(getImporter());
        JavaScopedMethods scopedMethods = new JavaScopedMethods(getImporter(), genericsList, inInterface());
        JavaScopedFields scopedFields = new JavaScopedFields(getImporter(), genericsList, inInterface(), scopedMethods);
        JavaImplementsList implementsList = new JavaImplementsList(getImporter(), keyword);

        this.genericsList = genericsList;
        this.scopedFields = scopedFields;
        this.scopedMethods = scopedMethods;
        this.implementsList = implementsList;

        modifierWith(Modifier.PUBLIC);
        annotateGenerated();
    }

    protected boolean inInterface() { return true; }

    @Override
    protected boolean isAllowModifierWith(Modifier modifier) { return modifier == Modifier.PUBLIC; }

    public JavaImplementsList getImplementsList() { return implementsList; }

    public JavaGenericsList getGenericsList() { return genericsList; }

    public JavaScopedFields fields() { return this.scopedFields; }

    public JavaScopedMethods methods() { return this.scopedMethods; }

    @Override
    public String getClassname() { return classname; }

    @Override
    public String toString() {
        JavaAddr addr = JavaAddr.newPackageOf(packageName).next(2);

        // import 块儿, import 放最后设置
        JavaAddr.Mark importMark = addr.mark();
        // 注释
        addDeclareBlockComments(addr.next());
        addDeclareDocComments(addr);
        // 注解
        addDeclareAnnotations(addr);
        // 类声明
        addDeclareJavaFileDefinition(addr);
        addr.add(" {").open();

        // 类定义
        fields().addDeclareFields(addr);
        methods().addDeclareMethods(addr);

        // 类结束
        addr.close();
        addr.newLine("}").next();

        // 应用 import
        importMark.with(getImporter().toString("\n"));
        return addr.toString();
    }

    private void addDeclareJavaFileDefinition(JavaAddr addr) {
        // 类声明
        addr.newLine("");
        addDeclareModifiers(addr);
        addDeclareClassname(addr);
        addDeclareGenericsList(addr);
        addDeclareSuperclass(addr);
        addDeclareImplementsInterfaces(addr);
    }

    protected void addDeclareImplementsInterfaces(JavaAddr addr) { getImplementsList().add(addr); }

    protected void addDeclareSuperclass(JavaAddr addr) { }

    protected final void addDeclareGenericsList(JavaAddr addr) { getGenericsList().add(addr); }

    protected final void addDeclareClassname(JavaAddr addr) {
        if (!addr.endWithSpaceChar()) {
            addr.add(SPACE_CHAR);
        }
        addr.add("interface ").add(simpleName);
    }
}
