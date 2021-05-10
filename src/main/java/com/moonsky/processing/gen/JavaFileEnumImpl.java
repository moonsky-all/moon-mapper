package com.moonsky.processing.gen;

import javax.lang.model.element.Modifier;

/**
 * @author benshaoye
 */
public class JavaFileEnumImpl extends JavaFileImplementation {

    protected JavaFileEnumImpl(String packageName, String simpleName) {
        super(packageName, simpleName, JavaElementEnum.ENUM);
    }

    @Override
    protected boolean isAllowModifierWith(Modifier modifier) {
        return modifier == Modifier.PUBLIC;
    }
}
