package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author benshaoye
 */
public abstract class BaseBlockCommentable extends BaseAnnotatedElement {

    private final List<String> blockComments = new ArrayList<>();
    private final List<String> docComments = new ArrayList<>();
    private boolean isAllBlank = true;

    public BaseBlockCommentable(
        Importer importer, JavaElementEnum elementEnum
    ) {
        super(importer, elementEnum);
    }

    public BaseBlockCommentable blockCommentsOf(Object... comments) {
        if (comments != null) {
            addCommentsToList(getBlockComments(), comments);
        }
        return this;
    }

    public BaseBlockCommentable docCommentsOf(Object... comments) {
        if (comments != null) {
            addCommentsToList(getDocComments(), comments);
        }
        return this;
    }

    public BaseBlockCommentable addBlockCommentOf(Object comment) {
        addCommentToList(getBlockComments(), comment);
        return this;
    }

    public BaseBlockCommentable addDocCommentOf(Object comment) {
        addCommentToList(getDocComments(), comment);
        return this;
    }

    public List<String> getBlockComments() { return blockComments; }

    public List<String> getDocComments() { return docComments; }

    private void addCommentsToList(List<String> list, Object[] comments) {
        for (Object comment : comments) {
            addCommentToList(list, comment);
        }
    }

    private void addCommentToList(List<String> list, Object comment) {
        if (comment == null) {
            list.add(null);
        } else if (comment instanceof Class<?>) {
            list.add(((Class<?>) comment).getCanonicalName());
            this.isAllBlank = false;
        } else {
            list.add(comment.toString());
            this.isAllBlank = false;
        }
    }

    @Override
    public void add(JavaAddr addr) {
        if (addBlockComments(addr)) {
            addr.next();
        }
        addDocComments(addr);
    }

    private boolean addBlockComments(JavaAddr addr) {
        return addComments(addr, getBlockComments(), "/*");
    }

    private boolean addDocComments(JavaAddr addr) {
        return addComments(addr, getBlockComments(), "/*");
    }

    private boolean addComments(JavaAddr addr, List<String> comments, String open) {
        if (isAllBlank || comments.isEmpty()) {
            return false;
        }
        String close = " */";
        if (comments.size() == 1) {
            String comment = comments.get(0);
            if (String2.isBlank(comment)) {
                return false;
            }
            if (addr.isLineOverLength(comment.length() + close.length() + open.length() + 1)) {
                addr.newLine(open);
                addr.newLine(" * ").add(comment);
                addr.newLine(close);
            } else {
                addr.newLine(open).add(' ').add(comment).add(close);
            }
        } else {
            addr.newLine(open);
            for (String comment : comments) {
                addr.newLine(" * ").add(comment);
            }
            addr.newLine(close);
        }
        return true;
    }
}
