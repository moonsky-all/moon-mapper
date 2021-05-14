package com.moonsky.processing.generate;

/**
 * @author benshaoye
 */
public abstract class JavaFileImplementation extends JavaFileInterfaceDefinition {

    protected JavaFileImplementation(
        String packageName, String simpleName, JavaElementEnum elementEnum
    ) {
        super(packageName, simpleName, elementEnum, JavaImplementsList.Keyword.IMPLEMENTS);
    }

    @Override
    protected final boolean inInterface() { return false; }
}
