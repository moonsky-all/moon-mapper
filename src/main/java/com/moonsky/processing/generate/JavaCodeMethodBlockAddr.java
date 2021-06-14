package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;

import java.util.function.Supplier;

/**
 * @author benshaoye
 */
public class JavaCodeMethodBlockAddr extends JavaCodeBlockAddr<JavaElemMethod> {

    public JavaCodeMethodBlockAddr(
        Importer importer,
        String signature,
        Supplier<VarSupplier<JavaElemField>> fieldsVarSupplier,
        JavaElemMethod ending,
        boolean returnable
    ) { super(importer, signature, fieldsVarSupplier, ending, returnable); }

    @Override
    public JavaCodeBlockAddr<JavaCodeMethodBlockAddr> onIfNotNull(String varName) {
        return cast(super.onIfNotNull(varName));
    }

    @Override
    public JavaCodeBlockAddr<JavaCodeMethodBlockAddr> onStringIfNotEmpty(String varName) {
        return cast(super.onStringIfNotEmpty(varName));
    }

    @Override
    public JavaCodeBlockAddr<JavaCodeMethodBlockAddr> onIf(String ifTemplate, Object... values) {
        return cast(super.onIf(ifTemplate, values));
    }

    @Override
    public JavaCodeBlockAddr<JavaCodeMethodBlockAddr> onElseIf(String elseIfTemplate, Object... values) {
        return cast(super.onElseIf(elseIfTemplate, values));
    }

    @Override
    public JavaCodeBlockAddr<JavaCodeMethodBlockAddr> onElse() {
        return cast(super.onElse());
    }

    @Override
    public JavaCodeBlockAddr<JavaCodeMethodBlockAddr> onFor(String forTemplate, Object... values) {
        return cast(super.onFor(forTemplate, values));
    }

    @Override
    public JavaCodeBlockAddr<JavaCodeMethodBlockAddr> onTry() {
        return cast(super.onTry());
    }

    @Override
    public JavaCodeBlockAddr<JavaCodeMethodBlockAddr> onTry(String tryTemplate, Object... values) {
        return cast(super.onTry(tryTemplate, values));
    }

    @Override
    public JavaCodeBlockAddr<JavaCodeMethodBlockAddr> onWhile(String whileTemplate, Object... values) {
        return cast(super.onWhile(whileTemplate, values));
    }

    @Override
    public JavaCodeBlockAddr<JavaCodeMethodBlockAddr> onCatch(String catchTemplate, Object... values) {
        return cast(super.onCatch(catchTemplate, values));
    }

    @Override
    public JavaCodeBlockAddr<JavaCodeMethodBlockAddr> onFinally() {
        return cast(super.onFinally());
    }

    @Override
    public JavaCodeMethodBlockAddr scriptOf(String scriptTemplate, Object... values) {
        return cast(super.scriptOf(scriptTemplate, values));
    }

    @Override
    public JavaCodeMethodBlockAddr keyScriptOf(String key, String scriptTemplate, Object... values) {
        return cast(super.keyScriptOf(key, scriptTemplate, values));
    }

    @Override
    public JavaCodeMethodBlockAddr returning(String returningTemplate, Object... values) {
        return cast(super.returning(returningTemplate, values));
    }
}
