package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;

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
    private String returnType, returning;

    public JavaElemMethod(
        Importer importer,
        JavaGenericsList genericsList,
        String methodName,
        JavaElemParametersList parameterList,
        boolean inInterface
    ) {
        super(importer, JavaElementEnum.METHOD, parameterList);
        this.genericsList = genericsList;
        this.methodName = methodName;
        this.inInterface = inInterface;
        this.signature = String.join("#", methodName, parameterList.getSignature());
    }

    public String getReturnType() { return returnType; }

    public JavaElemMethod typeOf(String returnTypeTemplate, Object... types) {
        this.returnType = TypeFormatter2.with(returnTypeTemplate, types);
        return this;
    }

    public JavaElemMethod returning(String scriptTemplate, Object... values) {
        this.returning = String2.format(scriptTemplate, values);
        return this;
    }

    public boolean inInterface() { return inInterface; }

    public String getSignature() { return signature; }

    @Override
    protected boolean isAllowModifierWith(Modifier modifier) {
        return (inInterface() ? INTERFACE_METHODS_MODIFIERS : METHODS_MODIFIERS).contains(modifier);
    }
}
