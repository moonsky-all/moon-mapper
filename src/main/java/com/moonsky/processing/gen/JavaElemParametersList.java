package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author benshaoye
 */
public class JavaElemParametersList extends AbstractImportable {

    private final Map<String, JavaElemParameter> parametersMap = new LinkedHashMap<>();
    /** 所在类的泛型声明, 方法的泛型声明 */
    private final JavaGenericsList enclosingGenericsList, executableGenericsList;
    /** 参数列表所在位置，指向具体某个方法或构造器 */
    private JavaElemExecutable executionCommentable;
    private boolean unmodifiable;

    public JavaElemParametersList(
        Importer importer, JavaGenericsList enclosingGenericsList, JavaGenericsList genericsList
    ) {
        super(importer);
        this.enclosingGenericsList = enclosingGenericsList;
        this.executableGenericsList = genericsList;
        this.unmodifiable = false;
    }

    void withElemExecutable(JavaElemExecutable executionCommentable) {
        if (this.executionCommentable == null) {
            this.executionCommentable = executionCommentable;
        }
    }

    public JavaElemParametersList add(String parameterName, String typeTemplate, Object... types) {
        return add(parameterName, p -> {}, typeTemplate, types);
    }

    public JavaElemParametersList add(String parameterName, Class<?> parameterType) {
        return add(parameterName, p -> {}, parameterType);
    }

    public JavaElemParametersList add(
        String parameterName, Consumer<JavaElemParameter> parameterCommenter, Class<?> parameterType
    ) {
        return add(parameterName, parameterCommenter, parameterType.getCanonicalName());
    }

    /**
     * @param parameterName      参数名
     * @param parameterCommenter 主要用于添加参数注解、添加参数注释
     * @param typeTemplate       参数类型
     * @param types              类型
     *
     * @return
     */
    public JavaElemParametersList add(
        String parameterName, Consumer<JavaElemParameter> parameterCommenter, String typeTemplate, Object... types
    ) {
        if (unmodifiable) {
            return this;
        }
        JavaElemParameter param = newParameter(parameterName, typeTemplate, types);
        parameterCommenter.accept(param);
        getParametersMap().put(parameterName, param);
        return this;
    }

    private Map<String, JavaElemParameter> getParametersMap() { return parametersMap; }

    private JavaElemParameter newParameter(String parameterName, String typeTemplate, Object... types) {
        return new JavaElemParameter(getImporter(),
            executionCommentable,
            enclosingGenericsList,
            executableGenericsList,
            this,
            parameterName,
            typeTemplate,
            types);
    }

    public void withUnmodifiable() { this.unmodifiable = true; }

    public String getSignature() {
        return parametersMap.values().stream().map(JavaElemParameter::getSignature).collect(Collectors.joining(","));
    }

    public int getAllAnnotationsCount() {
        int count = 0;
        for (JavaElemParameter value : getParametersMap().values()) {
            count += value.annotationsCount();
        }
        return count;
    }

    public boolean addDeclareElemParameter(JavaAddr addr) {
        final Map<String, JavaElemParameter> paramMap = getParametersMap();
        final int parametersCount = paramMap.size(), maxCount = 4;
        if (parametersCount == 0) {
            addr.add("()");
            return false;
        }
        final int annotationsCount = getAllAnnotationsCount();
        if (parametersCount > maxCount || annotationsCount > 0) {
            addr.add("(").open();
            for (JavaElemParameter value : paramMap.values()) {
                value.addDeclareAnnotatedParameter(addr);
                addr.add(',');
            }
            addr.deleteLastChar().close();
            addr.newLine(")");
        } else {
            int idx = 0;
            addr.add("(");
            for (JavaElemParameter value : paramMap.values()) {
                if (idx > 0) {
                    addr.add(' ');
                }
                value.addDeclareSimpleParameter(addr);
                addr.add(',');
                idx++;
            }
            addr.deleteLastChar().add(")");
        }
        return parametersCount > 0;
    }
}
