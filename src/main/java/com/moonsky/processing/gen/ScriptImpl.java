package com.moonsky.processing.gen;

import com.moonsky.processing.util.String2;

/**
 * @author benshaoye
 */
public class ScriptImpl implements Scripter {

    private final String line;
    private boolean sorted;

    public ScriptImpl(String lineScriptTemplate, Object ...values) {
        this.line = String2.format(lineScriptTemplate, values);
        this.sorted = true;
    }

    @Override
    public int length() { return line == null ? 0 : line.length(); }

    @Override
    public void withUnsorted() { this.sorted = false; }

    @Override
    public void addOnSimplify(JavaAddr addr) {
        addr.add(line);
    }

    @Override
    public void addOnMultiple(JavaAddr addr) {
        addr.newLine(line);
    }

    @Override
    public boolean isSorted() { return sorted; }

}
