package com.moonsky.mapper.processing.holder;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author benshaoye
 */
public enum Holders {
    /** default instance */
    INSTANCE;

    private final ThreadLocal<ProcessingEnvironment> ENV = new ThreadLocal<>();

    public void init(ProcessingEnvironment environment) { ENV.set(environment); }

    public final ProcessingEnvironment getEnvironment() { return ENV.get(); }

    public final Elements getUtils() {return getEnvironment().getElementUtils();}

    public final Types getTypes() {return getEnvironment().getTypeUtils();}
}
