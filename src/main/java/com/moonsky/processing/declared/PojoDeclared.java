package com.moonsky.processing.declared;

/**
 * @author benshaoye
 */
public class PojoDeclared extends BaseDeclarable {

    private final ClassDeclared classDeclared;

    PojoDeclared(ClassDeclared classDeclared) {
        super(classDeclared.getHolders(),
            classDeclared.getThisElement(),
            classDeclared.getEnclosingElement(),
            classDeclared.getThisGenericsMap());
        this.classDeclared = classDeclared;
    }

    public static PojoDeclared from(ClassDeclared classDeclared) {
        return new PojoHelper(classDeclared).toPojoDeclared();
    }

    public ClassDeclared getClassDeclared() { return classDeclared; }
}
