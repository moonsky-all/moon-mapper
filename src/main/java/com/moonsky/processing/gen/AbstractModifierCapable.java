package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

import javax.lang.model.element.Modifier;
import java.util.Set;
import java.util.TreeSet;

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
            Modifier2.useModifier(modifierSet, modifier);
        }
        return this;
    }

    /**
     * 包级别访问
     *
     * @return this
     */
    public AbstractModifierCapable packageAccessLevel() {
        modifierSet.removeAll(Modifier2.ACCESS_MODIFIERS);
        return this;
    }

    public final boolean has(Modifier modifier) {
        return modifierSet.contains(modifier);
    }

    public final boolean isStatic() { return has(Modifier.STATIC); }

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
}
