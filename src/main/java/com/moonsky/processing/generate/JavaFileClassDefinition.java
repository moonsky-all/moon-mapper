package com.moonsky.processing.generate;

import com.moonsky.processing.util.String2;

import javax.lang.model.element.Modifier;

/**
 * @author benshaoye
 */
public class JavaFileClassDefinition extends JavaFileImplementation {

    private String superclass;

    protected JavaFileClassDefinition(String packageName, String simpleName) {
        super(packageName, simpleName, JavaElementEnum.CLASS);
    }

    public JavaFileClassDefinition extendsOf(String superclassTemplate, Object... types) {
        this.superclass = TypeFormatter2.with(superclassTemplate, types);
        return this;
    }

    public String getSuperclass() { return superclass; }

    @Override
    protected boolean isAllowModifierWith(Modifier modifier) {
        return Modifier2.CLASS_MODIFIERS.contains(modifier);
    }

    @Override
    protected void addDeclareSuperclass(JavaAddr addr) {
        final String superclass = this.getSuperclass();
        if (String2.isBlank(superclass)) {
            return;
        }
        if (addr.isOverLength(superclass.length() + 7)) {
            addr.open();
            addr.newLine("");
        }
        addr.keepEndsWith(' ').add("extends ").add(onImported(superclass));
    }
}
