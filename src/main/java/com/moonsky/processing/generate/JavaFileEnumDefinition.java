package com.moonsky.processing.generate;

/**
 * @author benshaoye
 */
public class JavaFileEnumDefinition extends JavaFileImplementation {

    public JavaFileEnumDefinition(String packageName, String simpleName) {
        super(packageName, simpleName, JavaFileKeywordEnum.ENUM, JavaElementEnum.ENUM);
    }
}
