package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author benshaoye
 */
public abstract class BaseBlockCommentable extends BaseAnnotatedElement {

    private final List<String> blockComments = new ArrayList<>();
    private final List<String> docComments = new ArrayList<>();
    private final Map<String, List<JavaTypeComments>> throwsComments = new LinkedHashMap<>();
    private boolean commentAllBlank = true;

    public BaseBlockCommentable(
        Importer importer, JavaElementEnum elementEnum
    ) {
        super(importer, elementEnum);
    }

    private boolean isCommentAllBlank() { return commentAllBlank; }

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
        }
        if (comment instanceof CharSequence) {
            String str = comment.toString();
            if (String2.isBlank(str)) {
                list.add(null);
            } else {
                list.add(str);
                this.commentAllBlank = false;
            }
        } else if (comment instanceof Class<?>) {
            list.add(((Class<?>) comment).getCanonicalName());
            this.commentAllBlank = false;
        } else {
            list.add(comment.toString());
            this.commentAllBlank = false;
        }
    }

    protected boolean hasDocSupplementComments() { return false; }

    protected void addDocSupplementComments(JavaAddr addr) { }

    protected final boolean addDeclareBlockComments(JavaAddr addr) {
        boolean added = addComments(addr, getBlockComments(), "/*", false);
        if (added) {
            addr.next();
        }
        return added;
    }

    protected final boolean addDeclareDocComments(JavaAddr addr) {
        return addComments(addr, getDocComments(), "/**", true);
    }

    private boolean addComments(JavaAddr addr, List<String> comments, String open, boolean docAddr) {
        if (isCommentAllBlank() || comments.isEmpty()) {
            return false;
        }
        String close = " */";
        if (comments.size() > 1 || (docAddr && hasDocSupplementComments())) {
            addr.newLine(open);
            for (String comment : comments) {
                addr.newLine(" * ").add(comment);
            }
            if (docAddr) {
                addDocSupplementComments(addr);
            }
            addr.newLine(close);
        } else {
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
        }
        return true;
    }
}
