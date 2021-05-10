package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

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

    public boolean inInterface() { return inInterface; }

    public String getSignature() { return signature; }

    @Override
    protected boolean isAllowModifierWith(Modifier modifier) {
        return (inInterface() ? INTERFACE_METHODS_MODIFIERS : METHODS_MODIFIERS).contains(modifier);
    }
}
