package com.moonsky.processing.holder;

import com.moonsky.processing.declared.ClassDeclared;

import javax.lang.model.element.TypeElement;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public class ClassHolder extends AbstractHolder {

    private final Map<TypeElement, ClassDeclared> declaredMap = new IdentityHashMap<>();

    protected ClassHolder(Holders holders) {
        super(holders);
    }

    public ClassDeclared with(TypeElement typeElement) {
        ClassDeclared declared = declaredMap.get(typeElement);
        if (declared == null) {
            declared = ClassDeclared.from(getHolders(), typeElement);
            declaredMap.put(typeElement, declared);
        }
        return declared;
    }
}
