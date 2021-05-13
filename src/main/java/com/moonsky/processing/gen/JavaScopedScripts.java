package com.moonsky.processing.gen;

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
    private Scripter returning;

    public JavaScopedScripts(Importer importer, T executable) {
        super(importer, executable);
    }

    private Map<String, Scripter> getOriginScriptsMap() { return scripts; }

    public String scriptOf(String scriptTemplate, Object... values) {
        String key = varHelper.nextConst(getOriginScriptsMap().keySet());
        scriptOf(key, scriptTemplate, values);
        return key;
    }

    public JavaScopedScripts<T> scriptOf(String key, String scriptTemplate, Object... values) {
        getOriginScriptsMap().put(key, new ScriptImpl(scriptTemplate, values));
        return this;
    }

    public JavaScopedScripts<T> returning(String scriptTemplate, Object... values) {
        this.returning = new ScriptImpl("return " + scriptTemplate, values);
        return this;
    }

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
        addr.add(" {");

        // return 语句
        final Scripter returning = this.getReturning();
        Map<Boolean, List<Scripter>> scriptsMap = getGroupedScripts();
        // 不必要求顺序的语句
        List<Scripter> unsortedScriptList = scriptsMap.getOrDefault(false, emptyList());
        // 必须要保持顺序的语句
        List<Scripter> sortedScriptList = scriptsMap.getOrDefault(true, emptyList());

        if (unsortedScriptList.isEmpty() && sortedScriptList.isEmpty()) {
            if (returning == null) {
                addr.add(" ");
            } else if (addr.isOverLength(returning.length())) {
                doAdd(returning, addr, Scripter::addOnSimplify);
            } else {
                addr.open();
                doAdd(returning, addr, Scripter::addOnMultiple);
                addr.close();
                addr.newLine("");
            }
        } else {
            addr.open();
            unsortedScriptList.sort(Comparator.comparingInt(Scripter::length));
            for (Scripter scripter : unsortedScriptList) {
                doAdd(scripter, addr, Scripter::addOnMultiple);
            }
            for (Scripter scripter : sortedScriptList) {
                doAdd(scripter, addr, Scripter::addOnMultiple);
            }
            if (returning != null) {
                doAdd(returning, addr, Scripter::addOnMultiple);
            }
            addr.close();
            addr.newLine("");
        }

        addr.add("}");
    }

    private static void doAdd(Scripter scripter, JavaAddr addr, BiConsumer<Scripter, JavaAddr> consumer) {
        if (scripter != null && addr != null) {
            consumer.accept(scripter, addr);
            addr.end();
        }
    }
}
