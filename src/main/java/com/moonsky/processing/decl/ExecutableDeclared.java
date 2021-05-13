package com.moonsky.processing.decl;

import com.moonsky.processing.holder.AbstractHolder;
import com.moonsky.processing.holder.Holders;

import java.util.ArrayList;
import java.util.List;

/**
 * @author benshaoye
 */
public abstract class ExecutableDeclared extends AbstractHolder {

    private final List<ParameterDeclared> parametersDeclaredList;

    protected ExecutableDeclared(Holders holders) {
        super(holders);
        this.parametersDeclaredList = new ArrayList<>();
    }

    public ParameterDeclared getParameterAt(int index) {
        return getParametersDeclaredList().get(index);
    }

    public List<ParameterDeclared> getParametersDeclaredList() { return parametersDeclaredList; }
}
