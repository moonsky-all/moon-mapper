package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

/**
 * @author benshaoye
 */
public abstract class JavaElemExecutable extends BaseBlockCommentable {

    private final JavaElemParametersList parameterList;

    public JavaElemExecutable(
        Importer importer, JavaElementEnum elementEnum, JavaElemParametersList parameterList
    ) {
        super(importer, elementEnum);
        this.parameterList = parameterList;
        parameterList.withElemExecutable(new JavaTempTester(() -> this.isStatic()));
    }
}
