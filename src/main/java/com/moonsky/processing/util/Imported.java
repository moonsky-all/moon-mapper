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

    public final static boolean BEAN;
    public final static boolean LOMBOK;
    public final static boolean AUTOWIRED;
    public final static boolean QUALIFIER;
    public final static boolean GENERATED;
    public final static boolean JODA_TIME_1X;
    public final static boolean JODA_TIME_2X;
    public final static boolean SERVICE;
    public final static boolean COMPONENT;
    public final static boolean REPOSITORY;
    public final static boolean SAFE_VARARGS;
    public final static boolean CONFIGURATION;
    public final static boolean CONDITIONAL_ON_MISSING_BEAN;
    public final static boolean COPIER_IMPL_GENERATED;
    public final static boolean MAPPER_IMPL_GENERATED;

    static {
        LOMBOK = nonException(() -> Data.class.toString());
        CONFIGURATION = nonException(() -> Configuration.class.toString());
        CONDITIONAL_ON_MISSING_BEAN = nonException(() -> ConditionalOnMissingBean.class.toString());
        BEAN = nonException(() -> Bean.class.toString());
        GENERATED = nonException(() -> Generated.class.toString());
        JODA_TIME_1X = nonException(() -> ReadablePeriod.class.toString());
        JODA_TIME_2X = nonException(() -> YearMonth.class.toString());
        AUTOWIRED = nonException(() -> Autowired.class.toString());
        QUALIFIER = nonException(() -> Qualifier.class.toString());
        SERVICE = nonException(() -> Service.class.toString());
        COMPONENT = nonException(() -> Component.class.toString());
        REPOSITORY = nonException(() -> Repository.class.toString());
        SAFE_VARARGS = nonException(() -> SafeVarargs.class.toString());
        COPIER_IMPL_GENERATED = nonException(() -> Copier.class.toString());
        MAPPER_IMPL_GENERATED = nonException(() -> Mapper.class.toString());
    }

    public static boolean isImportedComponent() { return COMPONENT || REPOSITORY || SERVICE; }

    public static boolean isComponent(TypeElement element) {
        if (COMPONENT && element.getAnnotation(Component.class) != null) {
            return true;
        }
        if (REPOSITORY && element.getAnnotation(Repository.class) != null) {
            return true;
        }
        if (SERVICE && element.getAnnotation(Service.class) != null) {
            return true;
        }
        return false;
    }

    private static boolean nonException(Supplier<String> runner) {
        try {
            runner.get();
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }
}
