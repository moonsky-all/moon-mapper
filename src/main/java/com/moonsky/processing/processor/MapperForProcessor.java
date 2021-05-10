package com.moonsky.processing.processor;

import com.google.auto.service.AutoService;
import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.processing.decl.GenericDeclared;
import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.Generic2;
import com.moonsky.processing.util.Log2;
import com.moonsky.processing.util.OnceRunner;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static com.moonsky.processing.util.Extract2.getValueClasses;
import static javax.lang.model.SourceVersion.latestSupported;

/**
 * @author benshaoye
 */
@AutoService(Processor.class)
public class MapperForProcessor extends AbstractProcessor {

    private final static OnceRunner RUNNER = new OnceRunner();
    private final Holders holders = Holders.INSTANCE;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        holders.init(processingEnv);
    }

    @Override
    public boolean process(
        Set<? extends TypeElement> annotations, RoundEnvironment roundEnv
    ) {
        RUNNER.run(() -> {
            doProcessingMapperFor(roundEnv);
            Log2.warn("+++++++++++++++++++++++++++++++++++++++++");
        });
        return true;
    }

    private static void doProcessingMapperFor(RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(MapperFor.class).forEach(annotated -> {
            TypeElement mapperForAnnotated = (TypeElement) annotated;
            MapperFor mapperFor = mapperForAnnotated.getAnnotation(MapperFor.class);
            Collection<TypeElement> classes = getValueClasses(mapperForAnnotated, MapperFor.class, "value");
            Map<String, GenericDeclared> genericsMap = Generic2.from(mapperForAnnotated);
            genericsMap.forEach((key, decl) -> {
                Log2.warn("---------------->> {}", key);
                Log2.warn(decl);
            });
        });
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(MapperFor.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() { return latestSupported(); }
}
