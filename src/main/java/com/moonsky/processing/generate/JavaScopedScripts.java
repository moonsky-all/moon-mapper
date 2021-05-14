package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

/**
 * @author benshaoye
 */
public class JavaScopedScripts<T extends JavaElemExecutable> extends AbstractScoped<T> {

    private final VarHelper varHelper = new VarHelper();
    private final Map<String, Scripter> scripts = new LinkedHashMap<>();
    /**
     * 只有方法有返回值，其他的代码块、构造器是没有返回值的
     */
    private final boolean returnable;
    private Scripter returning;

    public JavaScopedScripts(Importer importer, T executable, boolean returnable) {
        super(importer, executable);
        this.returnable = returnable;
    }

    private Map<String, Scripter> getOriginScriptsMap() { return scripts; }

    public String scriptOf(String scriptTemplate, Object... values) {
        String key = varHelper.nextConst(getOriginScriptsMap().keySet());
        scriptOf(key, scriptTemplate, values);
        return key;
    }

    public JavaScopedScripts<T> scriptOf(String key, String scriptTemplate, Object... values) {
        getOriginScriptsMap().put(key, new ScriptImpl(getImporter(), scriptTemplate, values));
        return this;
    }

    public JavaScopedScripts<T> returning(String scriptTemplate, Object... values) {
        return setReturning(new ScriptImpl(getImporter(), "return " + scriptTemplate, values));
    }

    public JavaScopedScripts<T> returnNone() { return setReturning(null); }

    public JavaScopedScripts<T> setReturning(Scripter returning) {
        if (isReturnable()) {
            this.returning = returning;
        }
        return this;
    }

    public boolean isReturnable() { return returnable; }

    /**
     * 返回
     *
     * @return
     */
    private Map<Boolean, List<Scripter>> getGroupedScripts() {
        Map<Boolean, List<Scripter>> grouped = getOriginScriptsMap().values()
            .stream()
            .collect(Collectors.groupingBy(Scripter::isSorted));
        grouped.putIfAbsent(false, Collections.emptyList());
        grouped.putIfAbsent(true, Collections.emptyList());
        return grouped;
    }

    Scripter getReturning() { return returning; }

    public void addDeclaredMethodScripts(JavaAddr addr) {
        // return 语句
        final Scripter returning = this.getReturning();
        Map<Boolean, List<Scripter>> scriptsMap = getGroupedScripts();
        // 不必要求顺序的语句
        List<Scripter> unsortedScriptList = scriptsMap.getOrDefault(false, emptyList());
        // 必须要保持顺序的语句
        List<Scripter> sortedScriptList = scriptsMap.getOrDefault(true, emptyList());

        addr.add("{");
        if (unsortedScriptList.isEmpty() && sortedScriptList.isEmpty()) {
            if (returning == null) {
                addr.add(" ");
            } else if (addr.isOverLength(returning.length())) {
                doAdd(returning, addr, Scripter::addOnSimplify);
            } else {
                addr.open();
                doAdd(returning, addr, Scripter::addOnMultiply);
                addr.close();
                addr.newLine("");
            }
        } else {
            addr.open();
            unsortedScriptList.sort(Comparator.comparingInt(Scripter::length));
            for (Scripter scripter : unsortedScriptList) {
                doAdd(scripter, addr, Scripter::addOnMultiply);
            }
            for (Scripter scripter : sortedScriptList) {
                doAdd(scripter, addr, Scripter::addOnMultiply);
            }
            if (returning != null) {
                doAdd(returning, addr, Scripter::addOnMultiply);
            }
            addr.close();
            addr.newLine("");
        }

        addr.add("}");
    }

    private static void doAdd(Scripter scripter, JavaAddr addr, BiConsumer<Scripter, JavaAddr> consumer) {
        if (scripter != null && addr != null && scripter.isAvailable()) {
            consumer.accept(scripter, addr);
            addr.end();
        }
    }
}
