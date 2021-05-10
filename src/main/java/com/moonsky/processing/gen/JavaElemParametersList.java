package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author benshaoye
 */
public class JavaElemParametersList extends AbstractImportable {

    private final Map<String, JavaElemParameter> parametersMap = new LinkedHashMap<>();
    /** 所在类的泛型声明, 方法的泛型声明 */
    private final JavaGenericsList enclosingGenericsList, executableGenericsList;
    /** 参数列表所在位置，指向具体某个方法或构造器 */
    private JavaTempTester staticTester;
    private boolean unmodifiable;

    public JavaElemParametersList(
        Importer importer, JavaGenericsList enclosingGenericsList, JavaGenericsList genericsList
    ) {
        super(importer);
        this.enclosingGenericsList = enclosingGenericsList;
        this.executableGenericsList = genericsList;
        this.unmodifiable = false;
    }

    void withElemExecutable(JavaTempTester staticTester) {
        if (this.staticTester == null) {
            this.staticTester = staticTester;
        }
    }

    public JavaElemParametersList add(String parameterName, String typeTemplate, Object... types) {
        if (unmodifiable) {
            return this;
        }
        JavaElemParameter param = new JavaElemParameter(getImporter(),
            staticTester,
            enclosingGenericsList,
            executableGenericsList,
            parameterName,
            typeTemplate,
            types);
        this.parametersMap.put(parameterName, param);
        return this;
    }

    public void withUnmodifiable() { this.unmodifiable = true; }

    public String getSignature() {
        return parametersMap.values().stream().map(JavaElemParameter::getSignature).collect(Collectors.joining(","));
    }
}
