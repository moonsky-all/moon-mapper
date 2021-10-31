package com.moonsky.mapper.processor.holder;

import com.moonsky.processor.processing.holder.PojoClassHolder;

/**
 * @author benshaoye
 */
public abstract class AbstractHolder {

    private final MapperHolders holders;

    public AbstractHolder(MapperHolders holders) {
        this.holders = holders;
    }

    public MapperHolders getHolders() {return holders;}

    protected final PojoClassHolder pojoClassHolder() {
        return holders.pojoClassHolder();
    }

    protected final PojoCopierHolder pojoCopierHolder() {
        return holders.pojoCopierHolder();
    }

    protected final PojoMapperHolder pojoMapperHolder() {
        return holders.pojoMapperHolder();
    }
}
