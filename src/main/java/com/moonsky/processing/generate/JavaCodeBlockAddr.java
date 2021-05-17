package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static com.moonsky.processing.generate.JavaCodeKeyword.*;

/**
 * @author benshaoye
 */
public class JavaCodeBlockAddr<T> extends AbstractEndingImportable<T> implements JavaCodeAddr {

    private final static String RETURN = "return ";

    private final String key;
    private final JavaCodeKeyword keyword;
    /** 支持 if(), for(), while() */
    private final String condition;
    private final boolean returnable;
    private final Map<String, JavaCodeAddr> codesMap = new LinkedHashMap<>();
    private JavaCodeAddr returning;

    public JavaCodeBlockAddr(
        Importer importer, T value, boolean returnable
    ) { this(importer, value, null, JavaCodeKeyword.NONE, null, returnable); }

    private JavaCodeBlockAddr(
        Importer importer, T value, String key, JavaCodeKeyword keyword, String condition, boolean returnable
    ) {
        super(importer, value);
        this.returnable = returnable;
        this.condition = condition;
        this.keyword = keyword;
        this.key = key;
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onIf(String ifTemplate, Object... values) {
        return onKeyword(IF, ifTemplate, values);
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onElseIf(String elseIfTemplate, Object... values) {
        return onKeyword(ELSE_IF, elseIfTemplate, values);
    }

    public JavaCodeBlockAddr<JavaCodeBlockAddr<T>> onElse(String elseTemplate, Object... values) {
        return onKeyword(ELSE, elseTemplate, values);
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

    public JavaCodeBlockAddr<T> scriptOf(String scriptTemplate, Object... values) {
        return scriptOf(UUID.randomUUID().toString(), scriptTemplate, values);
    }

    public JavaCodeBlockAddr<T> scriptOf(String key, String scriptTemplate, Object... values) {
        codesMap.put(key, new JavaCodeLineAddr<>(getImporter(), end(), key, scriptTemplate, values));
        return this;
    }

    public JavaCodeBlockAddr<T> returnNone() { return returning(""); }

    public JavaCodeBlockAddr<T> returning(String returningTemplate, Object... values) {
        if (isReturnable()) {
            if (returningTemplate == null) {
                this.returning = new JavaCodeLineAddr<>(getImporter(), end(), null, "return null");
            } else {
                String template = returningTemplate.startsWith(RETURN) ? returningTemplate
                    : (RETURN + returningTemplate);
                this.returning = new JavaCodeLineAddr<>(getImporter(), end(), null, template, values);
            }
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
                if (keyword != null) {
                    keyword.add(addr, condition);
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
        if (isReturnable()) {
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
            this,
            UUID.randomUUID().toString(),
            keyword,
            condition,
            returnable);
    }
}
