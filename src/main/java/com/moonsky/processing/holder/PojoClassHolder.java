package com.moonsky.processing.holder;

import com.moonsky.processing.declared.PojoDeclared;

import javax.lang.model.element.TypeElement;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public class PojoClassHolder extends AbstractHolder {

    private final Map<TypeElement, PojoDeclared> pojoDeclaredMap = new IdentityHashMap<>();

    protected PojoClassHolder(Holders holders) { super(holders); }

    public PojoDeclared with(TypeElement element) {
        PojoDeclared pojoDeclared = pojoDeclaredMap.get(element);
        if (pojoDeclared == null) {
            pojoDeclared = PojoDeclared.from(classHolder().with(element));
            pojoDeclaredMap.put(element, pojoDeclared);
        }
        return pojoDeclared;
    }
}
