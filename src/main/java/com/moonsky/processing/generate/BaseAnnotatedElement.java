package com.moonsky.processing.generate;

import com.moonsky.mapper.annotation.CopierImplGenerated;
import com.moonsky.mapper.annotation.MapperImplGenerated;
import com.moonsky.processing.util.Imported;
import com.moonsky.processing.util.Importer;
import com.moonsky.processing.util.Processing2;
import com.moonsky.processing.util.String2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Generated;
import javax.annotation.processing.Processor;
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

    private Map<String, Collection<JavaAnnotation>> getAnnotationsMap() { return annotationsMap; }

    private Collection<JavaAnnotation> obtainAnnotations(String annotationName) {
        return getAnnotationsMap().computeIfAbsent(annotationName, k -> new ArrayList<>());
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

    public BaseAnnotatedElement annotateOf(Class<? extends Annotation> annotationClass) {
        return annotateOf(annotationClass.getCanonicalName());
    }

    public BaseAnnotatedElement annotateOf(
        Class<? extends Annotation> annotationClass, Consumer<JavaAnnotation> annotationUsing
    ) { return annotateOf(annotationClass.getCanonicalName(), annotationUsing); }

    public BaseAnnotatedElement annotateOf(String annotationName) {
        Collection<JavaAnnotation> annotations = obtainAnnotations(annotationName);
        if (annotations.isEmpty() || isRepeatable(annotationName)) {
            JavaAnnotation annotation = new JavaAnnotation(getImporter(), elementEnum, annotationName);
            annotations.add(annotation);
        }
        return this;
    }

    public BaseAnnotatedElement annotateOf(String annotationName, Consumer<JavaAnnotation> annotationUsing) {
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

    public final int annotationsCount() {
        Map<String, Collection<JavaAnnotation>> annotationsMap = this.annotationsMap;
        if (annotationsMap.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (Collection<JavaAnnotation> value : annotationsMap.values()) {
            count += value.size();
        }
        return count;
    }

    /*
     * annotations
     */

    public BaseAnnotatedElement annotateGenerated() { return annotateGeneratedBy(Processor.class); }

    public BaseAnnotatedElement annotateGeneratedBy(Class<?> target) {
        if (Imported.GENERATED) {
            annotateOf(Generated.class, annotation -> {
                annotation.stringOf("date", DATETIME);
                annotation.stringOf("value", target.getCanonicalName());
            });
        }
        return this;
    }

    public BaseAnnotatedElement annotateRepository() {
        if (Imported.REPOSITORY) {
            annotateOf(Repository.class);
        }
        return this;
    }

    public BaseAnnotatedElement annotateQualifier(String qualifierName) {
        if (Imported.QUALIFIER) {
            annotateOf(Qualifier.class, a -> a.stringOf("value", qualifierName));
        }
        return this;
    }

    public BaseAnnotatedElement annotateQualifierIfNotBlank(String qualifierName) {
        if (String2.isNotBlank(qualifierName)) {
            return annotateQualifier(qualifierName);
        }
        return this;
    }

    public BaseAnnotatedElement annotateAutowired() { return annotateAutowired(true); }

    public BaseAnnotatedElement annotateAutowired(boolean required) {
        if (Imported.AUTOWIRED) {
            annotateOf(Autowired.class, annotation -> {
                if (!required) {
                    annotation.falseOf("required");
                }
            });
        }
        return this;
    }

    public BaseAnnotatedElement annotateOverride() {
        annotateOf(Override.class);
        return this;
    }

    public BaseAnnotatedElement annotateCopierImplGenerated() {
        if (Imported.COPIER_IMPL_GENERATED) {
            annotateOf(CopierImplGenerated.class);
        }
        return this;
    }

    public BaseAnnotatedElement annotateMapperImplGenerated() {
        if (Imported.MAPPER_IMPL_GENERATED) {
            annotateOf(MapperImplGenerated.class);
        }
        return this;
    }

    public BaseAnnotatedElement annotateComponent() {
        if (Imported.COMPONENT) {
            annotateOf(Component.class);
        }
        return this;
    }

    protected final boolean addDeclareAnnotations(JavaAddr addr) {
        for (Collection<JavaAnnotation> annotations : getAnnotationsMap().values()) {
            annotations.forEach(annotation -> annotation.add(addr));
        }
        return !getAnnotationsMap().isEmpty();
    }
}
