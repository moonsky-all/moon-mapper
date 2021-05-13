package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.Test2;

import javax.lang.model.element.Modifier;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 可修饰的
 *
 * @author benshaoye
 */
public abstract class AbstractModifierCapable extends AbstractImportable {

    private final JavaElementEnum elementEnum;
    private final Set<Modifier> modifierSet = new TreeSet<>();

    public AbstractModifierCapable(Importer importer, JavaElementEnum elementEnum) {
        super(importer);
        this.elementEnum = elementEnum;
    }

    public AbstractModifierCapable modifierWith(Modifier modifier) {
        if (isAllowModifierWith(modifier)) {
            Modifier2.useModifier(getOriginModifierSet(), modifier);
        }
        return this;
    }

    /**
     * 包级别访问
     *
     * @return this
     */
    public AbstractModifierCapable packageAccessLevel() {
        getOriginModifierSet().removeAll(Modifier2.ACCESS_MODIFIERS);
        return this;
    }

    public final boolean has(Modifier modifier) { return getOriginModifierSet().contains(modifier); }

    public final boolean hasAny(Modifier modifier, Modifier...modifiers) {
        return Test2.hasAny(getOriginModifierSet(), modifier, modifiers);
    }

    public final boolean isNotExisted(Modifier modifier) { return !has(modifier); }

    public final boolean isModifierWithStatic() { return has(Modifier.STATIC); }

    public final boolean isModifierWithDefault() { return has(Modifier.DEFAULT); }

    private Set<Modifier> getOriginModifierSet() { return modifierSet; }

    protected Set<Modifier> getModifierSet() { return new TreeSet<>(getOriginModifierSet()); }

    /**
     * 是否允许使用该修饰符
     * <p>
     * 不同位置的不同元素允许的修饰符不一样
     *
     * @param modifier 将要使用的修饰符
     *
     * @return 允许时返回 true，否则返回 false
     */
    protected abstract boolean isAllowModifierWith(Modifier modifier);

    protected final boolean addDeclareModifiers(JavaAddr addr) {
        Set<Modifier> modifiers = getModifierSet();
        if (modifiers.isEmpty()) {
            return false;
        }
        addr.add(modifiers.stream().map(m -> m.name().toLowerCase()).collect(Collectors.joining(" ")));
        return true;
    }
}
