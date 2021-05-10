package com.moonsky.processing.gen;

import javax.lang.model.element.Modifier;

/**
 * @author benshaoye
 */
public class JavaFileClassImpl extends JavaFileImplementation {

    protected JavaFileClassImpl(String packageName, String simpleName) {
        super(packageName, simpleName, JavaElementEnum.CLASS);
    }

    @Override
    protected boolean isAllowModifierWith(Modifier modifier) {
        return Modifier2.CLASS_MODIFIERS.contains(modifier);
    }
}
