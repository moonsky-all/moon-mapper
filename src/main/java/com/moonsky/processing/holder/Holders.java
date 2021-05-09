package com.moonsky.processing.holder;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author benshaoye
 */
public enum Holders {
    /** default instance */
    INSTANCE;

    private final TypeHolder typeHolder = new TypeHolder(this);
    private final CopierHolder copierHolder = new CopierHolder(this);
    private final MapperHolder mapperHolder = new MapperHolder(this);

    private final ThreadLocal<ProcessingEnvironment> ENV = new ThreadLocal<>();

    public void init(ProcessingEnvironment environment) { ENV.set(environment); }

    public ProcessingEnvironment getEnvironment() { return ENV.get(); }

    public Elements getUtils() { return getEnvironment().getElementUtils(); }

    public Types getTypes() { return getEnvironment().getTypeUtils(); }

    public TypeHolder typeHolder() { return typeHolder; }

    public CopierHolder copierHolder() { return copierHolder; }

    public MapperHolder mapperHolder() { return mapperHolder; }
}
