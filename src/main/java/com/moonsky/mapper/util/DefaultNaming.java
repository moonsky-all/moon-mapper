package com.moonsky.mapper.util;

import com.moonsky.mapper.annotation.MapperNaming;

import java.lang.annotation.Annotation;

/**
 * @author benshaoye
 */
@SuppressWarnings("all")
public enum DefaultNaming implements MapperNaming {
    /** 默认命名策略 */
    DEFAULT;

    private static final String[] PREFIXES = {};
    public static final String SUFFIXES = "Entity|Model|VO|DO|BO|BO|DTO";

    public static MapperNaming defaultIfNull(MapperNaming naming) {
        return naming == null ? DEFAULT : naming;
    }

    @Override
    public String[] trimPrefixes() { return PREFIXES; }

    @Override
    public String[] trimSuffixes() { return SUFFIXES.split("|"); }

    @Override
    public String pattern() { return MapperNaming.DEFAULT_PATTERN; }

    @Override
    public Class<? extends Annotation> annotationType() { return MapperNaming.class; }
}
