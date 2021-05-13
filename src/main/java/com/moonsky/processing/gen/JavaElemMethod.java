package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.Log2;

import javax.lang.model.element.Modifier;

import static com.moonsky.processing.gen.Modifier2.INTERFACE_METHODS_MODIFIERS;
import static com.moonsky.processing.gen.Modifier2.METHODS_MODIFIERS;

/**
 * @author benshaoye
 */
public class JavaElemMethod extends JavaElemExecutable {

    private final boolean inInterface;
    private final JavaGenericsList genericsList;
    private final String methodName, signature;
    private final JavaScopedScripts<JavaElemMethod> scopedScripts;
    private String returnType;

    public JavaElemMethod(
        Importer importer,
        JavaGenericsList genericsList,
        String methodName,
        JavaElemParametersList parameterList,
        boolean inInterface
    ) {
        super(importer, JavaElementEnum.METHOD, parameterList);
        this.scopedScripts = new JavaScopedScripts(importer, this);
        this.genericsList = genericsList;
        this.methodName = methodName;
        this.inInterface = inInterface;
        this.returnType = "void";
        this.signature = String.join("#", methodName, parameterList.getSignature());
        Log2.warn(this.signature);
    }

    public String getReturnType() { return returnType; }

    public JavaElemMethod typeOf(Class<?> returnType) {
        return typeOf(returnType.getCanonicalName());
    }

    public JavaElemMethod typeOf(String returnTypeTemplate, Object... types) {
        String type = TypeFormatter2.with(returnTypeTemplate, types);
        this.returnType = type;
        if (scripts().getReturning() == null) {
            scripts().returning(TypeFormatter2.defaultVal(type));
        }
        return this;
    }

    public boolean inInterface() { return inInterface; }

    public String getMethodName() { return methodName; }

    public String getSignature() { return signature; }

    public JavaScopedScripts<JavaElemMethod> scripts() { return scopedScripts; }

    public JavaGenericsList getGenericsList() { return genericsList; }

    private JavaScopedScripts getScopedScripts() { return scopedScripts; }

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
            getScopedScripts().addDeclaredMethodScripts(addr);
        }
    }
}
