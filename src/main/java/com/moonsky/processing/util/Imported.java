package com.moonsky.processing.util;

import com.moonsky.mapper.annotation.Copier;
import com.moonsky.mapper.annotation.Mapper;
import lombok.Data;
import org.joda.time.ReadablePeriod;
import org.joda.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Generated;
import javax.lang.model.element.TypeElement;
import java.util.function.Supplier;

/**
 * @author benshaoye
 */
public enum Imported {
    ;

    public final static boolean LOMBOK;
    public final static boolean JODA_TIME_1X;
    public final static boolean JODA_TIME_2X;
    public final static boolean SPRING_BEAN;
    public final static boolean SPRING_AUTOWIRED;
    public final static boolean SPRING_QUALIFIER;
    public final static boolean SPRING_SERVICE;
    public final static boolean SPRING_COMPONENT;
    public final static boolean SPRING_REPOSITORY;
    public final static boolean SPRING_CONFIGURATION;
    public final static boolean SPRING_CONDITIONAL_ON_MISSING_BEAN;
    public final static boolean GENERATED;
    public final static boolean SAFE_VARARGS;
    public final static boolean COPIER_IMPL_GENERATED;
    public final static boolean MAPPER_IMPL_GENERATED;

    static {
        LOMBOK = isImported(() -> Data.class);
        JODA_TIME_1X = isImported(() -> ReadablePeriod.class);
        JODA_TIME_2X = isImported(() -> YearMonth.class);
        SPRING_BEAN = isImported(() -> Bean.class);
        SPRING_SERVICE = isImported(() -> Service.class);
        SPRING_AUTOWIRED = isImported(() -> Autowired.class);
        SPRING_QUALIFIER = isImported(() -> Qualifier.class);
        SPRING_COMPONENT = isImported(() -> Component.class);
        SPRING_REPOSITORY = isImported(() -> Repository.class);
        SPRING_CONFIGURATION = isImported(() -> Configuration.class);
        SPRING_CONDITIONAL_ON_MISSING_BEAN = isImported(() -> ConditionalOnMissingBean.class);
        GENERATED = isImported(() -> Generated.class);
        SAFE_VARARGS = isImported(() -> SafeVarargs.class);
        COPIER_IMPL_GENERATED = isImported(() -> Copier.class);
        MAPPER_IMPL_GENERATED = isImported(() -> Mapper.class);
    }

    public static boolean isImportedComponent() { return SPRING_COMPONENT || SPRING_REPOSITORY || SPRING_SERVICE; }

    public static boolean isComponent(TypeElement element) {
        if (SPRING_COMPONENT && element.getAnnotation(Component.class) != null) {
            return true;
        }
        if (SPRING_REPOSITORY && element.getAnnotation(Repository.class) != null) {
            return true;
        }
        return SPRING_SERVICE && element.getAnnotation(Service.class) != null;
    }

    private static boolean isImported(Supplier<Class<?>> runner) {
        try {
            return runner.get() != null;
        } catch (Throwable ignored) {
            return false;
        }
    }
}
