package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;

/**
 * @author benshaoye
 */
public class JavaCodeLineAddr<T> extends AbstractEndingImportable<T> implements JavaCodeAddr {

    private final String key;
    private final String line;

    public JavaCodeLineAddr(Importer importer, T value, String key,String lineCodeTemplate, Object... values) {
        super(importer, value);
        this.key = key;
        this.line = String2.formatImported(importer, lineCodeTemplate, values);
    }

    @Override
    public void add(JavaAddr addr) {
        addr.newLine(line).end();
    }

    @Override
    public String getKey() { return key; }
}
