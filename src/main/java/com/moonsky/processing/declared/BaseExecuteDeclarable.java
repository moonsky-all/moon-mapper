package com.moonsky.processing.declared;

import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.Test2;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.*;
import java.util.stream.Collectors;

import static com.moonsky.processing.util.Element2.getSimpleName;

/**
 * 包括方法和构造器
 * <p>
 * 其中方法包括普通方法和属性方法（setter & getter）
 *
 * @author benshaoye
 */
public abstract class BaseExecuteDeclarable extends BaseDeclarable {

    private final ExecutionEnum executionEnum;
    private final ExecutableElement executableElement;
    private final List<ParameterDeclared> parametersDeclaredList;
    private final Map<String, ParameterDeclared> parameterDeclaredMap;
    private final String parametersSignature;
    private final String methodName;

    protected BaseExecuteDeclarable(
        Holders holders,
        TypeElement thisElement,
        TypeElement enclosingElement,
        GenericsMap superGenericsMap,
        ExecutableElement executableElement,
        ExecutionEnum executionEnum
    ) {
        super(holders, thisElement, enclosingElement, new GenericsMap(executableElement, superGenericsMap));
        this.executableElement = executableElement;
        this.executionEnum = executionEnum;

        List<ParameterDeclared> parameters = parseParameters(executableElement);
        Map<String, ParameterDeclared> parametersMap = new LinkedHashMap<>();
        for (ParameterDeclared parameter : parameters) {
            parametersMap.put(parameter.getParameterName(), parameter);
        }
        this.parametersDeclaredList = Collections.unmodifiableList(parameters);
        this.parameterDeclaredMap = Collections.unmodifiableMap(parametersMap);

        this.methodName = Test2.isMethod(executableElement) ? getSimpleName(executableElement) : null;
        this.parametersSignature = parameters.stream()
            .map(ParameterDeclared::getSimplifyActualType)
            .collect(Collectors.joining(","));
    }

    private List<ParameterDeclared> parseParameters(ExecutableElement executableElement) {
        List<? extends VariableElement> parameters = executableElement.getParameters();
        List<ParameterDeclared> parametersDeclaredList = new ArrayList<>(parameters.size());
        for (VariableElement parameter : parameters) {
            parametersDeclaredList.add(new ParameterDeclared(getHolders(),
                getThisElement(),
                getEnclosingElement(),
                getThisGenericsMap(),
                executableElement,
                parameter,
                parametersDeclaredList.size()));
        }
        return parametersDeclaredList;
    }

    public ParameterDeclared getParameterAt(int index) {
        return getParametersDeclaredList().get(index);
    }

    public ParameterDeclared getParameterAs(String parameterName) {
        return getParameterDeclaredMap().get(parameterName);
    }

    public List<ParameterDeclared> getParametersDeclaredList() { return parametersDeclaredList; }

    public Map<String, ParameterDeclared> getParameterDeclaredMap() { return parameterDeclaredMap; }

    public final ExecutableElement getExecutableElement() { return executableElement; }

    public final ExecutionEnum getExecutionEnum() { return executionEnum; }

    public final String getMethodName() { return methodName; }

    public String getSignature() { return parametersSignature; }
}
