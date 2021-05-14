package com.moonsky.processing.declared;

import com.moonsky.processing.util.Generic2;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Map;

/**
 * @author benshaoye
 */
public class GenericsMap {

    private final TypeElement thisElement;
    private final Map<String, GenericDeclared> genericsMap;

    public GenericsMap(TypeElement thisElement) {
        this.genericsMap = Generic2.from(thisElement);
        this.thisElement = thisElement;
    }

    public GenericsMap(ExecutableElement executableElement, GenericsMap parent) {
        this.genericsMap = Generic2.from(executableElement, parent.genericsMap);
        this.thisElement = parent.thisElement;
    }

    private Map<String, GenericDeclared> getGenericsMap() { return genericsMap; }

    public String getActual(String enclosingClassname, String declareType) {
        return Generic2.mapToActual(getGenericsMap(), enclosingClassname, declareType);
    }
}
