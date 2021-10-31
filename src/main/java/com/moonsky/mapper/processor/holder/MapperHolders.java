package com.moonsky.mapper.processor.holder;

import com.moonsky.processor.processing.holder.PojoClassHolder;
import com.moonsky.processor.processing.holder.PublicHolders;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * @author benshaoye
 */
public enum MapperHolders {
    HOLDER;

    private final PublicHolders publicHolders = PublicHolders.INSTANCE;
    private final PojoCopierHolder copierHolder = new PojoCopierHolder(this);
    private final PojoMapperHolder mapperHolder = new PojoMapperHolder(this);
    private final AutoMapperHolder autoHolder = new AutoMapperHolder(this);

    public void init(ProcessingEnvironment environment) {publicHolders.init(environment);}

    public PojoClassHolder pojoClassHolder() {return publicHolders.pojoClassHolder();}

    public PojoCopierHolder pojoCopierHolder() {return this.copierHolder;}

    public PojoMapperHolder pojoMapperHolder() {return this.mapperHolder;}

    public AutoMapperHolder autoHolder() {return autoHolder;}
}
