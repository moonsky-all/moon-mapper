package com.moonsky.processing.processor;

import com.google.auto.service.AutoService;
import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.processing.declared.GenericDeclared;
import com.moonsky.processing.gen.JavaFileInterfaceDefinition;
import com.moonsky.processing.holder.Holders;
import com.moonsky.processing.util.Generic2;
import com.moonsky.processing.util.Log2;
import com.moonsky.processing.util.OnceRunner;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.math.BigDecimal;
import java.util.*;

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
            JavaFileInterfaceDefinition javaFile = new JavaFileInterfaceDefinition("com.moon.detail",
                "TestInterfaceDeclare");
            javaFile.fields().declareField("CONST_boolean", "boolean");
            javaFile.fields().declareField("CONST_char", "char");
            javaFile.fields().declareField("CONST_byte", "byte");
            javaFile.fields().declareField("CONST_short", "short");
            javaFile.fields().declareField("CONST_int", "int");
            javaFile.fields().declareField("CONST_long", "long");
            javaFile.fields().declareField("CONST_float", "float");
            javaFile.fields().declareField("CONST_double", "double");

            javaFile.methods()
                .declareMethod("defaultMethod", genericsList -> {
                    genericsList.add("T").add("E");
                }, p -> {
                    p.add("defaultName", "int");
                    p.add("defaultDouble", "double");
                    p.add("defaultByte", Byte.class);
                    p.add("defaultValue", String.class);
                    p.add("defaultBigDecimal", param -> {
                        param.annotateOf(SuppressWarnings.class, annotation -> {
                            annotation.stringOf("value", "all");
                        });
                    }, "{}<{}>", List.class, BigDecimal.class);
                })
                .blockCommentsOf("First method block comment line.", "Second method block comment line.")
                .docCommentsOf("First method doc comment line.", "Second method doc comment line.")
                .annotateOf(SuppressWarnings.class, annotation -> {
                    annotation.stringOf("value", "all");
                });
            javaFile.methods().declareMethod("hasBody", param -> {
                param.add("name", String.class);
                param.add("age", int.class);
            }).typeOf(int.class).modifierWith(Modifier.DEFAULT);
            JavaFiler.write(javaFile);
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
