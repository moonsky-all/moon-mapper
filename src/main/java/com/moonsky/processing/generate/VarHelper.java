package com.moonsky.processing.generate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.emptySet;

/**
 * @author benshaoye
 */
public class VarHelper {

    private final static Map<String, VarHelper> NAMESPACED_MAP = new ConcurrentHashMap<>();
    /**
     * 自定义变量缓存
     */
    private final Map<Object, String> memberCached = new HashMap<>();
    private final Map<Object, String> staticCached = new HashMap<>();
    /**
     * 变量命名空间，在同一个块级作用域内只能有一个 VarHelper
     */
    private final String namespace;
    private final String staticPrefix;
    private final String memberPrefix;
    private int memberIndex;
    private int staticIndex;

    private VarHelper(String namespace, String staticPrefix, String memberPrefix) {
        this.namespace = namespace;
        this.staticPrefix = staticPrefix;
        this.memberPrefix = memberPrefix;
    }

    public static VarHelper of(String namespace, String staticPrefix, String memberPrefix) {
        String namespaced = String.join(":", namespace, staticPrefix, memberPrefix);
        VarHelper helper = NAMESPACED_MAP.get(namespaced);
        if (helper == null) {
            helper = new VarHelper(namespace, staticPrefix, memberPrefix);
            NAMESPACED_MAP.put(namespaced, helper);
        }
        return helper;
    }

    public static VarHelper of(String namespace) {
        return of(namespace, "S", "m");
    }

    public String next() { return next(emptySet()); }

    public String next(Object key) { return next(key, emptySet()); }

    public String next(Set<String> excludesVars) {
        return toNext(this, excludesVars, false);
    }

    public String next(Object key, Set<String> excludesVars) {
        return computeInCached(this, key, excludesVars, false);
    }

    public String nextConst() { return nextConst(emptySet()); }

    public String nextConst(Object key) { return nextConst(key, emptySet()); }

    public String nextConst(Set<String> excludesVars) {
        return toNext(this, excludesVars, true);
    }

    public String nextConst(Object key, Set<String> excludesVars) {
        return computeInCached(this, key, excludesVars, true);
    }

    private static String computeInCached(
        VarHelper helper, Object key, Set<String> excludesVars, boolean wasStatic
    ) {
        Map<Object, String> caches = wasStatic ? helper.staticCached : helper.memberCached;
        String var = caches.get(key);
        if (var == null) {
            var = wasStatic ? helper.nextConst(excludesVars) : helper.next(excludesVars);
            caches.put(key, var);
        }
        return var;
    }

    private static String toNext(VarHelper helper, Set<String> excludesVars, boolean wasStatic) {
        String var;
        final String prefix = wasStatic ? helper.staticPrefix : helper.memberPrefix;
        final int init = wasStatic ? helper.staticIndex : helper.memberIndex;
        for (int i = init; ; i++) {
            var = prefix + i;
            if (!excludesVars.contains(var)) {
                if (wasStatic) {
                    helper.staticIndex = i + 1;
                } else {
                    helper.memberIndex = i + 1;
                }
                return var;
            }
        }
    }
}
