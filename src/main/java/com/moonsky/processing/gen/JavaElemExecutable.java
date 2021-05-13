package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

import java.util.*;

/**
 * @author benshaoye
 */
public abstract class JavaElemExecutable extends BaseBlockCommentable {

    private final JavaElemParametersList parameterList;

    private final Map<String, List<String>> docCommentForParams = new LinkedHashMap<>();
    private final List<String> returningComments = new ArrayList<>();

    public JavaElemExecutable(
        Importer importer, JavaElementEnum elementEnum, JavaElemParametersList parameterList
    ) {
        super(importer, elementEnum);
        this.parameterList = parameterList;
        parameterList.withElemExecutable(this);
    }

    final void paramCommentsOf(String parameterName, String... comments) {
        if (comments == null) {
            docCommentForParams.put(parameterName, new ArrayList<>());
        } else {
            docCommentForParams.put(parameterName, new ArrayList<>(Arrays.asList(comments)));
        }
    }

    public JavaElemParametersList getParameterList() { return parameterList; }

    @Override
    protected void addDocSupplementComments(JavaAddr addr) {

    }

    protected final boolean addDeclareExecutableParametersList(JavaAddr addr) {
        return getParameterList().addDeclareElemParameter(addr);
    }
}
