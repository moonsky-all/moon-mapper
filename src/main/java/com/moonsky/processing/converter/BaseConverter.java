package com.moonsky.processing.converter;

import com.moonsky.processing.declared.PropertyMethodDeclared;
import com.moonsky.processing.generate.JavaCodeBlockAddr;
import com.moonsky.processing.generate.JavaElemMethod;
import com.moonsky.processing.util.Imported;

/**
 * @author benshaoye
 */
public abstract class BaseConverter implements Converter {

    private static final String LOCAL_VAR_DECLARE = "{} {} = {}.{}()";
    protected final static String THIS = "thisObject", THAT = "thatObject";

    protected final String template;

    public BaseConverter(String template) { this.template = template; }

    protected static String nameOf(PropertyMethodDeclared methodDeclared) {
        return methodDeclared.getMethodName();
    }

    @SuppressWarnings("all")
    protected static <T> Imported<T> typeOf(PropertyMethodDeclared methodDeclared) {
        Imported imported = Imported.nameOf(methodDeclared.getPropertyActualType());
        return imported;
    }

    protected static String defineGetterValueVar(
        JavaCodeBlockAddr<JavaElemMethod> scripts, PropertyMethodDeclared getter
    ) {
        String var = scripts.varsHelper().next();
        scripts.scriptOf(LOCAL_VAR_DECLARE, typeOf(getter), var, THIS, nameOf(getter));
        return var;
    }
}
