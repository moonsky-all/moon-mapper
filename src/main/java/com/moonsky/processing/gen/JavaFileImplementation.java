package com.moonsky.processing.gen;

/**
 * @author benshaoye
 */
public abstract class JavaFileImplementation extends JavaFileInterfaceImpl {

    protected JavaFileImplementation(String packageName, String simpleName, JavaElementEnum elementEnum) {
        super(packageName, simpleName, elementEnum);
    }

    @Override
    protected final boolean inInterface() { return false; }
}
