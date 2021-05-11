package com.moonsky.processing.gen;

/**
 * @author benshaoye
 */
public class JavaFileEnumImpl extends JavaFileImplementation {

    protected JavaFileEnumImpl(String packageName, String simpleName) {
        super(packageName, simpleName, JavaElementEnum.ENUM);
    }
}
