package com.moonsky.processing.processor;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;

/**
 * Java 文件定义
 *
 * @author benshaoye
 * @see Filer#createSourceFile(CharSequence, Element...)
 * @see JavaFileObject#openWriter()
 */
public interface JavaDefinition {

    /**
     * 类全名，如: java.lang.String
     *
     * @return 类全名
     */
    String getClassname();

    /**
     * 类完整内容
     *
     * @return 类声明
     */
    @Override
    String toString();
}
