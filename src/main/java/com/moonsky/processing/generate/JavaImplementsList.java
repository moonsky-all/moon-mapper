package com.moonsky.processing.generate;

import com.moonsky.processing.util.Generic2;
import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.String2;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author benshaoye
 */
public class JavaImplementsList extends AbstractImportable implements Addable {

    private final Map<String, String> interfacesMap = new LinkedHashMap<>();
    private final Keyword interfaceKeyword;

    public JavaImplementsList(Importer importer, Keyword keyword) {
        super(importer);
        this.interfaceKeyword = keyword;
    }

    public JavaImplementsList implement(Class<?> interfaceClass) {
        return implement(interfaceClass.getCanonicalName());
    }

    public JavaImplementsList implement(String typeTemplate, Object... types) {
        String interfaceName = TypeFormatter2.with(typeTemplate, types);
        String typeSimplify = Generic2.typeSimplify(interfaceName);
        getInterfacesMap().put(typeSimplify, interfaceName);
        return this;
    }

    public Map<String, String> getInterfacesMap() { return interfacesMap; }

    @Override
    public void add(JavaAddr addr) {
        if (getInterfacesMap().isEmpty()) {
            return;
        }
        List<String> importedList = getInterfacesMap().values()
            .stream()
            .map(this::onImported)
            .collect(Collectors.toList());

        String keyword = interfaceKeyword.name().toLowerCase();
        if (importedList.size() > 3) {
            addr.open();
            addr.newLine(keyword);
            String placeholder = String2.toSpaceString(keyword.length());
            int idx = 0;
            for (String value : importedList) {
                addr.newLine(idx == 0 ? keyword : placeholder);
                addr.add(" ").add(value).add(",");
                idx++;
            }
            addr.deleteLastChar().close();
        } else {
            addr.add(" ").add(keyword);
            addr.add(" ").add(String.join(", ", importedList));
        }
    }

    public enum Keyword {
        EXTENDS,
        IMPLEMENTS
    }
}
