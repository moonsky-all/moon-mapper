package com.moonsky.processing.gen;

import javax.lang.model.element.Modifier;
import java.util.*;

/**
 * @author benshaoye
 */
enum Modifier2 {
    ;

    /** 互斥修饰符（不能同时存在的修饰符） */
    private static final Map<Modifier, List<Set<Modifier>>> MUTUALLY_EXCLUSIVE = new HashMap<>();

    static {
        mutuallyExclusive(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.PRIVATE);
        mutuallyExclusive(Modifier.STATIC, Modifier.DEFAULT, Modifier.ABSTRACT);
        mutuallyExclusive(Modifier.FINAL, Modifier.ABSTRACT);
        mutuallyExclusive(Modifier.FINAL, Modifier.VOLATILE);
    }

    /** 访问修饰符 */
    static final Set<Modifier> ACCESS_MODIFIERS = new HashSet<>();
    /** 接口方法允许的修饰符 */
    static final Set<Modifier> INTERFACE_METHODS_MODIFIERS = new HashSet<>();
    /** 普通类方法允许的修饰符 */
    static final Set<Modifier> METHODS_MODIFIERS = new HashSet<>();

    static {
        ACCESS_MODIFIERS.add(Modifier.PUBLIC);
        ACCESS_MODIFIERS.add(Modifier.PROTECTED);
        ACCESS_MODIFIERS.add(Modifier.PRIVATE);

        INTERFACE_METHODS_MODIFIERS.add(Modifier.STATIC);
        INTERFACE_METHODS_MODIFIERS.add(Modifier.DEFAULT);

        METHODS_MODIFIERS.add(Modifier.PUBLIC);
        METHODS_MODIFIERS.add(Modifier.PROTECTED);
        METHODS_MODIFIERS.add(Modifier.PRIVATE);
        METHODS_MODIFIERS.add(Modifier.STATIC);
        METHODS_MODIFIERS.add(Modifier.FINAL);
        METHODS_MODIFIERS.add(Modifier.SYNCHRONIZED);
    }

    /** 普通类允许的修饰符 */
    static final Set<Modifier> CLASS_MODIFIERS = new HashSet<>();

    static {
        CLASS_MODIFIERS.add(Modifier.PUBLIC);
        CLASS_MODIFIERS.add(Modifier.ABSTRACT);
        CLASS_MODIFIERS.add(Modifier.FINAL);
    }

    /** 普通类字段允许的修饰符 */
    static final Set<Modifier> CLASS_FIELD_MODIFIERS = new HashSet<>();

    static {
        CLASS_FIELD_MODIFIERS.add(Modifier.PUBLIC);
        CLASS_FIELD_MODIFIERS.add(Modifier.PROTECTED);
        CLASS_FIELD_MODIFIERS.add(Modifier.PRIVATE);
        CLASS_FIELD_MODIFIERS.add(Modifier.TRANSIENT);
        CLASS_FIELD_MODIFIERS.add(Modifier.STATIC);
        CLASS_FIELD_MODIFIERS.add(Modifier.FINAL);
        CLASS_FIELD_MODIFIERS.add(Modifier.VOLATILE);
    }

    private static void mutuallyExclusive(Modifier modifier, Modifier... modifiers) {
        Map<Modifier, List<Set<Modifier>>> exclusivesMap = MUTUALLY_EXCLUSIVE;
        EnumSet<Modifier> modifierEnumSet = EnumSet.of(modifier, modifiers);
        for (Modifier oneModifier : modifierEnumSet) {
            List<Set<Modifier>> willExclusives = exclusivesMap.get(oneModifier);
            if (willExclusives == null) {
                willExclusives = new ArrayList<>();
                exclusivesMap.put(oneModifier, willExclusives);
            }
            willExclusives.add(modifierEnumSet);
        }
    }

    /**
     * 添加修饰符，同时排除互斥的修饰符
     *
     * @param modifiersSet
     * @param usingModifier
     */
    public static void useModifier(Collection<Modifier> modifiersSet, Modifier usingModifier) {
        List<Set<Modifier>> willExclusivesList = MUTUALLY_EXCLUSIVE.get(usingModifier);
        if (willExclusivesList != null) {
            for (Set<Modifier> willExclusive : willExclusivesList) {
                modifiersSet.removeAll(willExclusive);
            }
        }
        modifiersSet.add(usingModifier);
    }
}
