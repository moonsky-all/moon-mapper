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

    public final Holders getHolders() { return holders; }

    protected final ClassHolder classHolder() { return holders.classHolder(); }

    protected final PojoCopierHolder pojoCopierHolder() { return holders.pojoCopierHolder(); }

    protected final PojoMapperHolder pojoMapperHolder() { return holders.pojoMapperHolder(); }

    protected final PojoClassHolder pojoClassHolder() { return holders.pojoClassHolder(); }
}
