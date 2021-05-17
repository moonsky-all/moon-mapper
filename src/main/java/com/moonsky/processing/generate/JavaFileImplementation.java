package com.moonsky.processing.generate;

/**
 * @author benshaoye
 */
public abstract class JavaFileImplementation extends JavaFileInterfaceDefinition {

    protected JavaFileImplementation(
        String packageName, String simpleName, JavaFileKeywordEnum javaKeyword, JavaElementEnum elementEnum
    ) {
        super(packageName, simpleName, javaKeyword, elementEnum, JavaImplementsList.Keyword.IMPLEMENTS);
    }

    @Override
    protected final boolean inInterface() { return false; }
}
