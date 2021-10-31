package com.moonsky.mapper.processor.definition;

import com.moonsky.mapper.processor.holder.AbstractHolder;
import com.moonsky.mapper.processor.holder.MapperHolders;
import com.moonsky.processor.processing.declared.ClassDeclared;
import com.moonsky.processor.processing.declared.PojoDeclared;

/**
 * @author benshaoye
 */
public abstract class PojoBaseDefinition extends AbstractHolder {

    private final PojoDeclared thisPojoDecl;
    private final PojoDeclared thatPojoDecl;
    private final String packageName;

    protected PojoBaseDefinition(
        MapperHolders holders, PojoDeclared thisPojoDecl, PojoDeclared thatPojoDecl
    ) {
        super(holders);
        this.thisPojoDecl = thisPojoDecl;
        this.thatPojoDecl = thatPojoDecl;
        this.packageName = thisPojoDecl.getClassDeclared().getThisPackageName();
    }

    public ClassDeclared getThisDeclared() {return getThisPojoDeclared().getClassDeclared();}

    public ClassDeclared getThatDeclared() {return getThatPojoDeclared().getClassDeclared();}

    public String getThisClassname() {return getThisDeclared().getThisClassname();}

    public String getThatClassname() {return getThatDeclared().getThisClassname();}

    public PojoDeclared getThisPojoDeclared() {return thisPojoDecl;}

    public PojoDeclared getThatPojoDeclared() {return thatPojoDecl;}

    public final String getPackageName() {return packageName;}
}
