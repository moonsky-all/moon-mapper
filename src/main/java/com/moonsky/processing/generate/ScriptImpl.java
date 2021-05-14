package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;

/**
 * @author benshaoye
 */
public class ScriptImpl implements Scripter {

    private final String line;
    private boolean available;
    private boolean sorted;

    public ScriptImpl(Importer importer, String lineScriptTemplate, Object... values) {
        this.line = String2.formatImported(importer, lineScriptTemplate, values);
        this.available = true;
        this.sorted = true;
    }

    @Override
    public boolean isAvailable() { return available; }

    @Override
    public String getLineScript() { return line; }

    @Override
    public int length() { return line == null ? 0 : line.length(); }

    @Override
    public void withUnsorted() { this.sorted = false; }

    @Override
    public void withUnavailable() { this.available = false; }

    @Override
    public void addOnSimplify(JavaAddr addr) { addr.add(line); }

    @Override
    public void addOnMultiply(JavaAddr addr) { addr.newLine(line); }

    @Override
    public boolean isSorted() { return sorted; }
}
