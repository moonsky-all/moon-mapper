package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static com.moonsky.processing.generate.JavaCodeKeyword.*;

/**
 * @author benshaoye
 */
public class JavaCodeBlockAddr<T> extends AbstractEndingImportable<T> implements JavaCodeAddr {

    private final static String RETURN = "return ";

    /** 方法签名 */
    private final String signature;
    /** 方法级别局部变量 */
    private final VarHelper vars;
    /** 方法所在类的字段管理器 */
    private final Supplier<VarSupplier<JavaElemField>> fieldsVarSupplier;
    /** 语句 key，存入{@link #codesMap}的 key */
    private final String key;
    private final JavaCodeKeyword thisKeyword;
    /** 支持 if(), for(), while() */
    private final String condition;
    /** 是否可返回 */
    private final boolean returnable;
    private final Map<String, JavaCodeAddr> codesMap = new LinkedHashMap<>();
    private JavaCodeAddr returning;

    public JavaCodeBlockAddr(
        Importer importer, String signature, Supplier<VarSupplier<JavaElemField>> fieldsVarSupplier, T ending, boolean returnable
    ) { this(importer, signature, fieldsVarSupplier, ending, null, JavaCodeKeyword.NONE, null, returnable); }

    private JavaCodeBlockAddr(
        Importer importer,
        String signature,
        Supplier<VarSupplier<JavaElemField>> fieldsVarSupplier,
        T value,
        String key,
        JavaCodeKeyword keyword,
        String condition,
        boolean returnable
    ) {
        super(importer, value);
        this.returnable = returnable;
        this.signature = signature;
        this.condition = condition;
        this.thisKeyword = keyword;
        this.key = key;
        this.fieldsVarSupplier = fieldsVarSupplier;
        this.vars = VarHelper.of(signature, "val", "var");
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onIfNotNull(String varName) {
        return onIf("{} != null", varName);
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onStringIfNotEmpty(String varName) {
        return onIf("{} != null && {}.length() > 0", varName, varName);
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onIf(String ifTemplate, Object... values) {
        return onKeyword(IF, ifTemplate, values);
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onElseIf(String elseIfTemplate, Object... values) {
        return onKeyword(ELSE_IF, elseIfTemplate, values);
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onElse() {
        return onKeyword(ELSE, null);
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onFor(String forTemplate, Object... values) {
        return onKeyword(FOR, forTemplate, values);
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onTry() { return onTry(null); }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onTry(String tryTemplate, Object... values) {
        return onKeyword(TRY, tryTemplate, values);
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onWhile(String whileTemplate, Object... values) {
        return onKeyword(WHILE, whileTemplate, values);
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onCatch(String catchTemplate, Object... values) {
        return onKeyword(CATCH, catchTemplate, values);
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onFinally() {
        return onKeyword(FINALLY, null);
    }

    public VarHelper varsHelper() { return vars; }

    public VarSupplier<JavaElemField> fieldsHelper() { return fieldsVarSupplier.get(); }

    public JavaCodeBlockAddr<T> scriptOf(String scriptTemplate, Object... values) {
        return keyScriptOf(UUID.randomUUID().toString(), scriptTemplate, values);
    }

    public JavaCodeBlockAddr<T> keyScriptOf(String key, String scriptTemplate, Object... values) {
        codesMap.put(key, new JavaCodeLineAddr<>(getImporter(), end(), key, scriptTemplate, values));
        return this;
    }

    public void returnNone() { this.returning = null; }

    public JavaCodeBlockAddr<T> returnEnd() { return returning(""); }

    public JavaCodeBlockAddr<T> returning(String returningTemplate, Object... values) {
        if (isReturnable()) {
            if (returningTemplate == null) {
                this.returning = new JavaCodeLineAddr<>(getImporter(), end(), null, "return null");
            } else {
                String template = returningTemplate.startsWith(RETURN) ? returningTemplate
                    : (RETURN + returningTemplate);
                this.returning = new JavaCodeLineAddr<>(getImporter(), end(), null, template, values);
            }
        } else {
            this.returning = new JavaCodeLineAddr<>(getImporter(), end(), null, "return");
        }
        return this;
    }

    @Override
    public String getKey() { return key; }

    @Override
    public boolean isMultiply() { return true; }

    public boolean isReturnable() { return returnable; }

    public JavaCodeAddr getReturning() { return returning; }

    @Override
    public void add(JavaAddr addr) {
        codesMap.forEach((key, codeAddr) -> {
            if (codeAddr.isMultiply()) {
                if (codeAddr instanceof JavaCodeBlockAddr<?>) {
                    JavaCodeKeyword codeKeyword = ((JavaCodeBlockAddr<?>) codeAddr).thisKeyword;
                    if (codeKeyword != null) {
                        codeKeyword.add(addr, ((JavaCodeBlockAddr<?>) codeAddr).condition);
                    }
                }
                addr.add("{");
                addr.open();
                codeAddr.add(addr);
                addr.close();
                addr.newLine("}");
            } else {
                codeAddr.add(addr);
            }
        });
        if (isReturnable() && getReturning() != null) {
            returning.add(addr);
        }
    }

    private JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onKeyword(
        JavaCodeKeyword keyword, String template, Object... values
    ) {
        String condition = String2.formatImported(getImporter(), template, values);
        JavaCodeBlockAddr<JavaCodeBlockAddr<T>> codeAddr = newBlockAddr(keyword, condition, returnable);
        codesMap.put(codeAddr.getKey(), codeAddr);
        return codeAddr;
    }

    private JavaCodeBlockAddr<JavaCodeBlockAddr<T>> newBlockAddr(
        JavaCodeKeyword keyword, String condition, boolean returnable
    ) {
        return new JavaCodeBlockAddr<>(getImporter(),
            signature,
            fieldsVarSupplier,
            this,
            UUID.randomUUID().toString(),
            keyword,
            condition,
            returnable);
    }
}
