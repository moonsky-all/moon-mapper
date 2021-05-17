package com.moonsky.processing.generate;

import com.moonsky.processing.util.String2;

/**
 * @author benshaoye
 */
public enum JavaCodeKeyword {
    /** java 关键字 */
    NONE(null) {
        @Override
        public void add(JavaAddr addr, String condition) { }
    },
    STATIC("static") {
        @Override
        public void add(JavaAddr addr, String condition) {
            addr.add(getText()).add(SPACE);
        }
    },
    WHILE("while"),
    FOR("for"),
    IF("if"),
    ELSE_IF("else if") {
        @Override
        public void add(JavaAddr addr, String condition) {
            addr.add(SPACE).add(getText()).add(SPACE).add("(").add(condition).add(")");
        }
    },
    ELSE("else") {
        @Override
        public void add(JavaAddr addr, String condition) {
            addr.add(SPACE).add(getText()).add(SPACE);
        }
    },
    TRY("try") {
        @Override
        public void add(JavaAddr addr, String condition) {
            addr.newLine(getText()).add(SPACE);
            if (String2.isNotBlank(condition)) {
                addr.add("(").add(condition).add(") ");
            }
        }
    },
    CATCH("catch") {
        @Override
        public void add(JavaAddr addr, String condition) {
            addr.add(SPACE).add(getText()).add(SPACE).add("(").add(condition).add(")").add(SPACE);
        }
    },
    FINALLY("FINALLY") {
        @Override
        public void add(JavaAddr addr, String condition) {
            addr.add(SPACE).add(getText()).add(SPACE);
        }
    };

    private final static String SPACE = " ";

    public final String keyword;

    JavaCodeKeyword(String keyword) { this.keyword = keyword; }

    public String getText() { return keyword; }

    public void add(JavaAddr addr, String condition) {
        addr.newLine(getText()).add(SPACE).add("(").add(condition).add(")").add(SPACE);
    }
}
