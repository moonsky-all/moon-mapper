package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;

import javax.lang.model.element.Modifier;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author benshaoye
 */
public class JavaScopedMethods extends JavaScopedMembers<JavaElemMethod> {

    public JavaScopedMethods(Importer importer, JavaGenericsList enclosingGenericsList, boolean inInterface) {
        super(importer, enclosingGenericsList, inInterface);
    }

    public JavaElemMethod declareMethod(String methodName) {
        return declareMethod(methodName, p -> {});
    }

    public JavaElemMethod declareMethod(String methodName, Consumer<JavaElemParametersList> parametersBuilder) {
        return declareMethod(methodName, l -> {}, parametersBuilder);
    }

    public JavaElemMethod declareMethod(
        String methodName,
        Consumer<JavaGenericsList> genericsBuilder,
        Consumer<JavaElemParametersList> parametersBuilder
    ) {
        JavaElemMethod method = newMethod(methodName, genericsBuilder, parametersBuilder);
        method.modifierWith(Modifier.PUBLIC);
        getMemberMap().put(method.getSignature(), method);
        return method;
    }

    public void remove(JavaElemMethod method) {
        remove(method.getSignature());
    }

    private JavaElemMethod newMethod(
        String methodName,
        Consumer<JavaGenericsList> genericsBuilder,
        Consumer<JavaElemParametersList> parametersBuilder
    ) {
        JavaGenericsList genericsList = new JavaGenericsList(getImporter());
        genericsBuilder.accept(genericsList);
        JavaElemParametersList parametersList = new JavaElemParametersList(getImporter(),
            getEnclosingGenericsList(),
            genericsList);
        parametersBuilder.accept(parametersList);
        parametersList.withUnmodifiable();
        return new JavaElemMethod(getImporter(), genericsList, methodName, parametersList, inInterface());
    }

    public void addDeclareMethods(JavaAddr addr) {
        Map<String, JavaElemMethod> methods = getMemberMap();
        if (methods.isEmpty()) {
            return;
        }
        for (Map.Entry<String, JavaElemMethod> methodEntry : methods.entrySet()) {
            addr.next();
            methodEntry.getValue().addDeclareMethod(addr);
        }
    }
}
