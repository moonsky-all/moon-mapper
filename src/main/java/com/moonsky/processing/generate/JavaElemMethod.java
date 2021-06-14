package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.Test2;

import javax.lang.model.element.Modifier;
import java.util.function.Supplier;

import static com.moonsky.processing.generate.Modifier2.INTERFACE_METHODS_MODIFIERS;
import static com.moonsky.processing.generate.Modifier2.METHODS_MODIFIERS;

/**
 * @author benshaoye
 */
public class JavaElemMethod extends JavaElemExecutable {

    private final boolean inInterface;
    private final JavaGenericsList genericsList;
    private final String methodName, signature;
    private final JavaCodeMethodBlockAddr methodBlockAddr;
    private String returnType;

    public JavaElemMethod(
        Importer importer,
        String classname,
        Supplier<VarSupplier<JavaElemField>> varsSupplier,
        JavaGenericsList genericsList,
        String methodName,
        JavaElemParametersList parameterList,
        boolean inInterface
    ) {
        super(importer, classname, JavaElementEnum.METHOD, parameterList);
        this.signature = String.join("#", methodName, parameterList.getSignature());
        this.methodBlockAddr = new JavaCodeMethodBlockAddr(importer, signature, varsSupplier, this, true);
        this.genericsList = genericsList;
        this.inInterface = inInterface;
        this.methodName = methodName;
        typeOf(void.class);
    }

    @Override
    public String getScopedNamespace() {
        return String.join(":", getClassname(), getSignature());
    }

    public String getReturnType() { return returnType; }

    public JavaElemMethod typeOf(Class<?> returnType) {
        return typeOf(returnType.getCanonicalName());
    }

    public JavaElemMethod typeOf(String returnTypeTemplate, Object... types) {
        String type = TypeFormatter2.with(returnTypeTemplate, types);
        JavaCodeBlockAddr<JavaElemMethod> scripts = scripts();
        if (Test2.isVoid(type)) {
            scripts.returnNone();
        } else if (scripts.getReturning() == null) {
            scripts.returning(TypeFormatter2.defaultVal(type));
        }
        this.returnType = type;
        return this;
    }

    public boolean inInterface() { return inInterface; }

    public String getMethodName() { return methodName; }

    public String getSignature() { return signature; }

    public JavaCodeMethodBlockAddr scripts() { return methodBlockAddr; }

    public JavaGenericsList getGenericsList() { return genericsList; }

    private JavaCodeMethodBlockAddr getScopedScripts() { return methodBlockAddr; }

    @Override
    protected boolean isAllowModifierWith(Modifier modifier) {
        return (inInterface() ? INTERFACE_METHODS_MODIFIERS : METHODS_MODIFIERS).contains(modifier);
    }

    public void addDeclareMethod(JavaAddr addr) {
        // comments & annotations
        addDeclareBlockComments(addr);
        addDeclareDocComments(addr);
        addDeclareAnnotations(addr);

        // method signature
        addDeclareModifiers(addr.newLine(""));
        getGenericsList().add(addr.keepEndsWith(' '));
        addr.keepEndsWith(' ').add(onImported(getReturnType()));
        addr.keepEndsWith(' ').add(getMethodName());
        addDeclareExecutableParametersList(addr);

        // method body
        if (inInterface() && !hasAny(Modifier.DEFAULT, Modifier.STATIC)) {
            addr.end();
        } else if (has(Modifier.ABSTRACT)) {
            addr.end();
        } else {
            addr.add(" {").open();
            getScopedScripts().add(addr);
            addr.close();
            addr.newLine("}");
        }
    }
}
