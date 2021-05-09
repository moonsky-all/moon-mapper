package com.moonsky.processing.holder;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author benshaoye
 */
public abstract class AbstractHolder {

    private final Holders holders;

    protected AbstractHolder(Holders holders) { this.holders = holders; }

    public final Elements getUtils() { return getHolders().getUtils(); }

    public final Types getTypes() { return getHolders().getTypes(); }

    protected final Holders getHolders() { return holders; }

    protected final TypeHolder typeHolder() { return holders.typeHolder(); }

    protected final CopierHolder copierHolder() { return holders.copierHolder(); }

    protected final MapperHolder mapperHolder() { return holders.mapperHolder(); }
}
