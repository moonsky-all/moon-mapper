package com.moonsky.mapper.processor;

import com.google.auto.service.AutoService;
import com.moonsky.mapper.annotation.Mapper;
import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.mapper.processor.holder.MapperHolders;
import com.moonsky.mapper.util.DefaultNaming;
import com.moonsky.processor.processing.BaseProcessorAdapter;
import com.moonsky.processor.processing.util.Collect2;
import com.moonsky.processor.processing.util.Extract2;
import com.moonsky.processor.processing.util.OnceProcessor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;

/**
 * 不建议在程序中使用包{@code com.moonsky.processing}以及子包中的任何内容
 * <p>
 * 以后很可能将{@code com.moonsky.processing}剥离出去，作为一个单独的模块
 *
 * @author benshaoye
 */
@SuppressWarnings("all")
@AutoService(Processor.class)
public class MoonskyMapperForProcessor extends BaseProcessorAdapter {

    private final static OnceProcessor PROCESSOR = new OnceProcessor();
    private final MapperHolders holders = MapperHolders.HOLDER;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        holders.init(processingEnv);
    }

    @Override
    public boolean process(
        Set<? extends TypeElement> annotations, RoundEnvironment roundEnv
    ) {
        PROCESSOR.process(() -> {
            doProcessingMapper(roundEnv);
            doProcessingMapperFor(roundEnv);
            doWriteJavaFiles();
        });
        return true;
    }

    private void doWriteJavaFiles() {
        holders.pojoCopierHolder().write();
        holders.pojoMapperHolder().write();
    }

    private void doProcessingMapper(RoundEnvironment roundEnv) {
        for (Element annotated : roundEnv.getElementsAnnotatedWith(Mapper.class)) {
            holders.autoHolder().with(annotated);
        }
    }

    private void doProcessingMapperFor(RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(MapperFor.class).forEach(annotated -> {
            TypeElement mapperForAnnotated = (TypeElement) annotated;
            MapperNaming naming = DefaultNaming.defaultIfNull(mapperForAnnotated.getAnnotation(MapperNaming.class));
            Collection<TypeElement> classes = Extract2.getValueClasses(mapperForAnnotated, MapperFor.class, "value");
            for (TypeElement element : classes) {
                holders.pojoMapperHolder().with(naming, mapperForAnnotated, element);
            }
        });
    }

    @Override
    protected Collection<? extends Class<? extends Annotation>> supportedAnnotationTypes() {
        return Collect2.list(MapperFor.class, Mapper.class);
    }
}
