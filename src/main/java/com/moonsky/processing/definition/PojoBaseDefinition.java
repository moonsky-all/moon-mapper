package com.moonsky.processing.definition;

import com.moonsky.processing.declared.ClassDeclared;
import com.moonsky.processing.declared.PojoDeclared;
import com.moonsky.processing.holder.AbstractHolder;
import com.moonsky.processing.holder.Holders;

/**
 * @author benshaoye
 */
public abstract class PojoBaseDefinition extends AbstractHolder {

    protected final static String THIS = "thisObject", THAT = "thatObject";
    private final PojoDeclared thisPojoDecl;
    private final PojoDeclared thatPojoDecl;
    private final String packageName;

    protected PojoBaseDefinition(
        Holders holders, PojoDeclared thisPojoDecl, PojoDeclared thatPojoDecl
    ) {
        super(holders);
        this.thisPojoDecl = thisPojoDecl;
        this.thatPojoDecl = thatPojoDecl;
        this.packageName = thisPojoDecl.getClassDeclared().getThisPackageName();
    }

    public ClassDeclared getThisDeclared() { return getThisPojoDeclared().getClassDeclared(); }

    public ClassDeclared getThatDeclared() { return getThatPojoDeclared().getClassDeclared(); }

    public String getThisClassname() { return getThisDeclared().getThisClassname(); }

    public String getThatClassname() { return getThatDeclared().getThisClassname(); }

    public PojoDeclared getThisPojoDeclared() { return thisPojoDecl; }

    public PojoDeclared getThatPojoDeclared() { return thatPojoDecl; }

    public final String getPackageName() { return packageName; }
}
