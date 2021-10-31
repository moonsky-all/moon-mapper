package com.moonsky.mapper.processor.holder;

import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.processor.processing.util.Element2;
import com.moonsky.processor.processing.util.Processing2;
import com.moonsky.processor.processing.util.String2;
import com.moonsky.processor.processing.util.Test2;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import static com.moonsky.mapper.util.DefaultNaming.defaultIfNull;
import static com.moonsky.processor.processing.util.AliasConstant2.BeanCopier_ClassName;
import static com.moonsky.processor.processing.util.AliasConstant2.BeanMapper_ClassName;

/**
 * @author benshaoye
 */
public class AutoMapperHolder extends AbstractHolder {

    private final static char LEFT = '<';

    public AutoMapperHolder(MapperHolders holders) {super(holders);}

    public void with(Element mapperElementAnnotated) {
        VariableElement elem = (VariableElement) mapperElementAnnotated;
        TypeMirror mirror = elem.asType();
        Element fieldElem = Processing2.getTypes().asElement(mirror);
        if (!(fieldElem instanceof TypeElement)) {
            throw new UnsupportedOperationException(String2.format("字段{} 未知类型: {}", elem, fieldElem));
        }
        TypeElement fieldTypeElem = (TypeElement) fieldElem;
        String classname = Element2.getQualifiedName(fieldTypeElem);
        if (BeanMapper_ClassName.equals(classname) || BeanCopier_ClassName.equals(classname)) {
            TypeElement[] elements = walkTypes(mirror, elem);
            MapperNaming naming = defaultIfNull(elements[0].getAnnotation(MapperNaming.class));
            pojoMapperHolder().with(naming, elements[0], elements[1]);
        } else {
            String template = "只能将 @Mapper 注解在 {} 或 {} 类型变量（字段/参数）上";
            throw new UnsupportedOperationException(String2.format(template,
                BeanMapper_ClassName,
                BeanCopier_ClassName));
        }
    }

    private static TypeElement[] walkTypes(TypeMirror mirror, VariableElement fieldElement) {
        String mirrorStringify = mirror.toString();
        final int start = mirrorStringify.indexOf(LEFT) + 1;
        if (start < 1) {
            String template = "必须为{} @Mapper {} {} 指定泛型类型";
            String type = Test2.isField(fieldElement) ? "字段" : "参数";
            throw new UnsupportedOperationException(String2.format(template, type, mirror, fieldElement));
        }
        final int length = mirrorStringify.length();
        final int comma = ',', right = '>', space = ' ';
        TypeElement thisClass = null;
        TypeElement thatClass = null;
        int leftCnt = 0;
        int expected;
        char current;
        StringBuilder naming = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for (int i = start; i < length; i++) {
            current = mirrorStringify.charAt(i);
            temp.append(current);
            if (leftCnt > 0) {
                if (current == right) {
                    leftCnt--;
                }
                continue;
            }
            if (current == LEFT) {
                leftCnt++;
                continue;
            }
            expected = thisClass == null ? comma : right;
            if (expected == current) {
                if (thisClass == null) {
                    thisClass = Element2.getTypeElement(naming.toString());
                    naming = new StringBuilder();
                } else {
                    thatClass = Element2.getTypeElement(naming.toString());
                    break;
                }
            } else if (expected != space) {
                naming.append(current);
            }
        }
        if (thatClass == null) {
            throw new UnsupportedOperationException(String2.format("[{} {}]未知映射类型: {} -> {}",
                mirror,
                fieldElement,
                thisClass,
                null));
        }
        return new TypeElement[]{thisClass, thatClass};
    }
}
