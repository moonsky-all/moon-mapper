package com.moonsky.processing.declared;

import com.moonsky.processing.holder.AbstractHolder;
import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.Element2;

import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;

import static com.moonsky.processing.util.Element2.getQualifiedName;

/**
 * @author benshaoye
 */
public abstract class BaseDeclarable extends AbstractHolder {

    /**
     * this 类（this 类指解析根类）
     */
    private final TypeElement thisElement;
    /**
     * 所属类，指的是字段实际声明类
     */
    private final TypeElement enclosingElement;
    /**
     * 所有泛型声明，如声明在 java.util.List&lt;T> 上的 T 在实际应用中如果是：
     * <p>
     * List&lt;String> 那么会这样包含:
     * <p>
     * java.util.List#T  ==  java.lang.String
     * java.util.Collection#T  ==  java.lang.String
     * java.util.Iterable#T  ==  java.lang.String
     * ...(所有父类、继承的接口都有其对应的声明和使用时的实际类)
     */
    private final GenericsMap thisGenericsMap;
    /**
     * @see #thisElement
     * @see Element2#getQualifiedName(QualifiedNameable)
     */
    private final String thisClassname;
    /**
     * @see #enclosingElement
     * @see Element2#getQualifiedName(QualifiedNameable)
     */
    private final String enclosingClassname;

    protected BaseDeclarable(
        Holders holders, TypeElement thisElement, TypeElement enclosingElement, GenericsMap thisGenericsMap
    ) {
        super(holders);
        this.thisElement = thisElement;
        this.enclosingElement = enclosingElement;
        this.thisGenericsMap = thisGenericsMap;
        this.thisClassname = getQualifiedName(thisElement);
        this.enclosingClassname = getQualifiedName(enclosingElement);
    }

    protected final String getMappedActual(String declareType) {
        return getThisGenericsMap().getActual(getEnclosingClassname(), declareType);
    }

    public final String getThisClassname() { return thisClassname; }

    public final String getEnclosingClassname() { return enclosingClassname; }

    public final TypeElement getThisElement() { return thisElement; }

    public final TypeElement getEnclosingElement() { return enclosingElement; }

    public final GenericsMap getThisGenericsMap() { return thisGenericsMap; }
}
