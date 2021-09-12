package com.moonsky.mapper.processor.definition;

import com.moonsky.mapper.processor.holder.AbstractHolder;
import com.moonsky.mapper.processor.holder.MapperHolders;
import com.moonsky.processor.processing.declared.ClassDeclared;
import com.moonsky.processor.processing.declared.PojoDeclared;
import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeBlockAddr;
import com.moonsky.processor.processing.generate.CodeLineNullOrElseHelper;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.generate.ElemMethod;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.String2;

/**
 * @author benshaoye
 */
public abstract class PojoBaseDefinition extends AbstractHolder {

    protected final static String THIS = "thisObject", THAT = "thatObject";
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

    protected final static CodeLineNullOrElseHelper<CodeBlockAddr<? extends CodeBlockAddr<ElemMethod>>> onNull(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, String var
    ) {return scripts.onNullOrVerify(THAT, setter.getMethodName(), var);}

    protected final static void setValueOf(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, String template, Object... values
    ) {
        scripts.onPresetOf("{}.{}({}.valueOf({}))",
            THAT,
            setter.getMethodName(),
            Imported.nameOf(setter.getPropertyActualType())).of(template, values);
    }

    protected final static String doGetterVal(PropertyMethodDeclared getter) {
        return String2.format("{}.{}()", THIS, getter.getMethodName());
    }

    protected final static String doGetterVal(PropertyMethodDeclared getter, String cast) {
        return String2.isBlank(cast) ? doGetterVal(getter) : (cast + doGetterVal(getter));
    }
}
