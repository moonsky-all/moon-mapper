package com.moonsky.processing;

import com.google.auto.service.AutoService;
import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.OnceRunner;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static com.moonsky.processing.util.Extract2.getValueClasses;
import static javax.lang.model.SourceVersion.latestSupported;

/**
 * 不建议在程序中使用包{@code com.moonsky.processing}以及子包中的任何内容
 *
 * 以后很可能将{@code com.moonsky.processing}剥离出去，作为一个单独的模块
 *
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
            doWriteJavaFiles();
        });
        return true;
    }

    private void doWriteJavaFiles() {
        holders.pojoCopierHolder().write();
        holders.pojoMapperHolder().write();
    }

    private void doProcessingMapperFor(RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(MapperFor.class).forEach(annotated -> {
            TypeElement mapperForAnnotated = (TypeElement) annotated;
            MapperFor mapperFor = mapperForAnnotated.getAnnotation(MapperFor.class);
            Collection<TypeElement> classes = getValueClasses(mapperForAnnotated, MapperFor.class, "value");
            for (TypeElement element : classes) {
                holders.pojoMapperHolder().with(mapperFor, mapperForAnnotated, element);
            }
        });
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(MapperFor.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() { return latestSupported(); }
}