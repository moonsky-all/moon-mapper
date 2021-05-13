package com.moonsky.processing.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author benshaoye
 */
public class JavaAddr {

    private final static int MAX_LINE_LENGTH = 120;
    private final static int INDENT_SIZE = 4;
    private final List<Mark> marks = new ArrayList<>();
    private final StringBuilder builder = new StringBuilder();
    private final String indentSize;
    private final int maxLineLength;
    private int indentUnit = 0;

    public JavaAddr() { this(INDENT_SIZE, MAX_LINE_LENGTH); }

    public JavaAddr(int indentSize, int maxLineLength) {
        char[] chars = new char[indentSize];
        Arrays.fill(chars, ' ');
        this.indentSize = new String(chars);
        this.maxLineLength = maxLineLength;
    }

    public static JavaAddr newPackageOf(String packageName) {
        return new JavaAddr().packageOf(packageName);
    }

    public JavaAddr packageOf(String packageName) {
        builder.setLength(0);
        return add("package ").add(packageName).end();
    }

    public JavaAddr newLine(Object lineScript) {
        return next().indent().add(lineScript);
    }

    public JavaAddr add(Object script) {
        builder.append(script);
        return this;
    }

    public JavaAddr next() { return next(1); }

    @SuppressWarnings("all")
    public JavaAddr next(int n) {
        switch (n) {
            case 3:
                builder.append('\n');
            case 2:
                builder.append('\n');
            case 1:
                builder.append('\n');
                break;
            default:
                char[] chars = new char[n];
                Arrays.fill(chars, '\n');
                builder.append(chars);
                break;
        }
        return this;
    }

    public JavaAddr indent() {
        for (int i = 0; i < indentUnit; i++) {
            builder.append(indentSize);
        }
        return this;
    }

    public JavaAddr keepEndsWith(char value) {
        return getLastChar() == value ? this : add(value);
    }

    public int getIndentSize() {
        return indentSize.length() * indentUnit;
    }

    public boolean isOverLength(int willAddLength) {
        int index = builder.lastIndexOf("\n");
        if (index < 0) {
            return false;
        }
        return (builder.length() - (index + 1) + willAddLength) > maxLineLength;
    }

    public boolean isLineOverLength(int lineLength) {
        return (getIndentSize() + lineLength) > maxLineLength;
    }

    /**
     * 结束语句
     *
     * @return
     */
    public JavaAddr end() {
        StringBuilder str = this.builder;
        int lastIndex = str.length() - 1;
        for (; lastIndex >= 0; lastIndex--) {
            char ch = str.charAt(lastIndex);
            if (!Character.isWhitespace(ch)) {
                if (ch == ';') {
                    return this;
                }
                str.append(';');
                break;
            }
        }
        return this;
    }

    public JavaAddr deleteLastChar() {
        return deleteCharAt(lastIndex());
    }

    public JavaAddr deleteCharAt(int index) {
        builder.deleteCharAt(index);
        return this;
    }

    public boolean endWithSpaceChar() { return getLastChar() == ' '; }

    public char getLastChar() { return charAt(lastIndex()); }

    public char charAt(int index) { return builder.charAt(index); }

    public int length() { return builder.length(); }

    public int lastIndex() { return length() - 1; }

    public void open() { indentUnit++; }

    public void close() { indentUnit--; }

    @Override
    public String toString() { return builder.toString(); }

    /**
     * 标记，可通过{@link Mark#with(CharSequence)}向标记处插入字符串等数据
     *
     * @return 标记
     */
    public Mark mark() { return new Mark(); }

    public final class Mark {

        private final int index;
        private final int indentUnit;

        private int position = JavaAddr.this.builder.length();

        private volatile boolean markReplaced = false;

        private Mark() {
            this.index = JavaAddr.this.marks.size();
            this.indentUnit = JavaAddr.this.indentUnit;
            JavaAddr.this.marks.add(this);
        }

        private boolean wasReplaced() {
            if (markReplaced) {
                return true;
            }
            synchronized (this) {
                if (markReplaced) {
                    return true;
                }
                this.markReplaced = true;
            }
            return false;
        }

        public void with(CharSequence script) {
            if (wasReplaced()) {
                return;
            }
            doReplace(script);
        }

        private void doReplace(CharSequence script) {
            builder.insert(position, script);
            List<Mark> marks = JavaAddr.this.marks;
            int appendedLength = script.length();
            int lastIdx = marks.size() - 1;
            for (int i = lastIdx; i > index; i--) {
                marks.get(i).position += appendedLength;
            }
        }
    }
}
