package com.moonsky.processing.gen;

/**
 * @author benshaoye
 */
public class JavaFileEnumDefinition extends JavaFileImplementation {

    protected JavaFileEnumDefinition(String packageName, String simpleName) {
        super(packageName, simpleName, JavaElementEnum.ENUM);
    }
}
