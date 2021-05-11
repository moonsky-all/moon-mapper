package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public class JavaGenericsList extends AbstractImportable implements Addable {

    private final Map<String, JavaGeneric> genericsMap = new LinkedHashMap<>();

    public JavaGenericsList(Importer importer) { super(importer); }

    public JavaGenericsList add(String declareName, String actualTypeTemplate, Object... types) {
        JavaGeneric generic = new JavaGeneric(getImporter(),
            declareName,
            TypeFormatter2.with(actualTypeTemplate, types));
        genericsMap.put(declareName, generic);
        return this;
    }

    public JavaGeneric get(String declareName) {
        return genericsMap.get(declareName);
    }

    private Map<String, JavaGeneric> getGenericsMap() { return genericsMap; }

    @Override
    public void add(JavaAddr addr) {
        Map<String, JavaGeneric> genericMap = getGenericsMap();
        if (genericMap.isEmpty()) {
            return;
        }
        addr.add("<");
        for (Map.Entry<String, JavaGeneric> genericEntry : genericMap.entrySet()) {
            genericEntry.getValue().add(addr);
            addr.add(',');
        }
        addr.deleteLastChar().add(">");
    }
}
