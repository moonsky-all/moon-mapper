package com.moonsky.processing.gen;

import com.moonsky.processing.util.Generic2;
import com.moonsky.processing.util.Importer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public class JavaImplementsList extends AbstractImportable {

    private final Map<String, String> interfacesMap = new LinkedHashMap<>();

    public JavaImplementsList(Importer importer) {
        super(importer);
    }

    public void implement(String typeTemplate, Object... types) {
        String interfaceName = TypeFormatter2.with(typeTemplate, types);
        String typeSimplify = Generic2.typeSimplify(interfaceName);
        getInterfacesMap().put(typeSimplify, interfaceName);
    }

    public Map<String, String> getInterfacesMap() { return interfacesMap; }
}
