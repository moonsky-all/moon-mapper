package com.moonsky.processing.gen;

import com.moonsky.mapper.annotation.CopierImplGenerated;
import com.moonsky.mapper.annotation.MapperImplGenerated;
import com.moonsky.processing.processor.MapperForProcessor;
import com.moonsky.processing.util.Imported;
import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.Processing2;
import com.moonsky.processing.util.String2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.Generated;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

/**
 * 可注解元素
 *
 * @author benshaoye
 */
public abstract class BaseAnnotatedElement extends AbstractModifierCapable {

    private final static String DATETIME = LocalDateTime.now().toString();
    private final static Map<String, Boolean> REPEATABLE_ANNOTATIONS = new HashMap<>();
    private final Map<String, Collection<JavaAnnotation>> annotationsMap = new LinkedHashMap<>();
    private final JavaElementEnum elementEnum;
    private final Elements utils;

    public BaseAnnotatedElement(Importer importer, JavaElementEnum elementEnum) {
        super(importer, elementEnum);
        this.elementEnum = elementEnum;
        this.utils = Processing2.getUtils();
    }

    private Collection<JavaAnnotation> obtainAnnotations(String annotationName) {
        return annotationsMap.computeIfAbsent(annotationName, k -> new ArrayList<>());
    }

    /**
     * 是否可重复注解
     *
     * @param annotationName
     *
     * @return
     */
    private boolean isRepeatable(String annotationName) {
        Boolean repeatable = REPEATABLE_ANNOTATIONS.get(annotationName);
        if (repeatable == null) {
            TypeElement element = utils.getTypeElement(annotationName);
            if (element == null) {
                repeatable = false;
            } else {
                repeatable = element.getAnnotation(Repeatable.class) != null;
            }
            REPEATABLE_ANNOTATIONS.put(annotationName, repeatable);
        }
        return repeatable;
    }

    public BaseAnnotatedElement annotationOf(Class<? extends Annotation> annotationClass) {
        return annotationOf(annotationClass.getCanonicalName());
    }

    public BaseAnnotatedElement annotationOf(
        Class<? extends Annotation> annotationClass, Consumer<JavaAnnotation> annotationUsing
    ) { return annotationOf(annotationClass.getCanonicalName(), annotationUsing); }

    public BaseAnnotatedElement annotationOf(String annotationName) {
        JavaAnnotation annotation = new JavaAnnotation(getImporter(), elementEnum, annotationName);
        Collection<JavaAnnotation> annotations = obtainAnnotations(annotationName);
        if (annotations.isEmpty() || isRepeatable(annotationName)) {
            annotations.add(annotation);
        }
        return this;
    }

    public BaseAnnotatedElement annotationOf(String annotationName, Consumer<JavaAnnotation> annotationUsing) {
        Collection<JavaAnnotation> annotations = obtainAnnotations(annotationName);
        if (annotations.isEmpty() || isRepeatable(annotationName)) {
            JavaAnnotation annotation = new JavaAnnotation(getImporter(), elementEnum, annotationName);
            annotationUsing.accept(annotation);
            annotations.add(annotation);
        }
        return this;
    }

    /**
     * 是否有注解
     *
     * @return true/false
     */
    public final boolean hasAnnotations() {
        if (annotationsMap.isEmpty()) {
            return false;
        }
        for (Collection<JavaAnnotation> value : annotationsMap.values()) {
            if (!value.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /*
     * annotations
     */

    public BaseAnnotatedElement annotationGenerated() {
        if (Imported.GENERATED) {
            annotationOf(Generated.class, annotation -> {
                annotation.stringOf("date", DATETIME);
                annotation.stringOf("value", MapperForProcessor.class);
            });
        }
        return this;
    }

    public BaseAnnotatedElement annotationRepository() {
        if (Imported.REPOSITORY) {
            annotationOf(Repository.class);
        }
        return this;
    }

    public BaseAnnotatedElement annotationQualifier(String qualifierName) {
        if (Imported.QUALIFIER) {
            annotationOf(Qualifier.class, a -> a.stringOf("value", qualifierName));
        }
        return this;
    }

    public BaseAnnotatedElement annotationQualifierIfNotBlank(String qualifierName) {
        if (String2.isNotBlank(qualifierName)) {
            return annotationQualifier(qualifierName);
        }
        return this;
    }

    public BaseAnnotatedElement annotationAutowired() { return annotationAutowired(true); }

    public BaseAnnotatedElement annotationAutowired(boolean required) {
        if (Imported.AUTOWIRED) {
            annotationOf(Autowired.class, annotation -> {
                if (!required) {
                    annotation.falseOf("required");
                }
            });
        }
        return this;
    }

    public BaseAnnotatedElement annotationOverride() {
        annotationOf(Override.class);
        return this;
    }

    public BaseAnnotatedElement annotationCopierImplGenerated() {
        if (Imported.COPIER_IMPL_GENERATED) {
            annotationOf(CopierImplGenerated.class);
        }
        return this;
    }

    public BaseAnnotatedElement annotationMapperImplGenerated() {
        if (Imported.COPIER_IMPL_GENERATED) {
            annotationOf(MapperImplGenerated.class);
        }
        return this;
    }

    public void add(JavaAddr addr) {

    }
}
