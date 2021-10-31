package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Element2;
import com.moonsky.processor.processing.util.Generic2;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.Test2;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
final class MethodConversionDetail extends BaseConversion implements Conversion {

    private final String convertClass;
    private final String convertMethodName;
    protected final String fromClassname;
    protected final String toClassname;
    private final boolean fromUsable;
    private final boolean toUsable;

    MethodConversionDetail(String convertClass, ExecutableElement method, TypeMirror fromMirror, TypeMirror toMirror) {
        this.fromClassname = fromMirror.toString();
        this.toClassname = toMirror.toString();
        this.convertClass = convertClass;
        this.convertMethodName = method.getSimpleName().toString();

        String simpleToClassname = Generic2.typeSimplify(this.toClassname);
        String simpleFromClassname = Generic2.typeSimplify(this.fromClassname);
        TypeElement fromElement = Element2.getTypeElement(simpleFromClassname);
        TypeElement toElement = Element2.getTypeElement(simpleToClassname);
        this.fromUsable = fromElement != null || Test2.isPrimitiveClass(fromClassname);
        this.toUsable = toElement != null || Test2.isPrimitiveClass(toClassname);
    }

    public String getConvertClass() {return convertClass;}

    public String getConvertMethodName() {return convertMethodName;}

    @Override
    public void register(
        ConversionRegistry conversionRegistry
    ) {
        if (fromUsable && toUsable) {
            conversionRegistry.register(this.fromClassname, this.toClassname, this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        Imported<String> convertClass = Imported.nameOf(getConvertClass());
        if (Test2.isPrimitiveClass(getter.getPropertyActualType())) {
            scripts.scriptOf("{}.{}({}.{}({}.{}()))", THAT, setter.getMethodName(),

                convertClass, getConvertMethodName(), THIS, getter.getMethodName());
        } else if (Test2.isPrimitiveClass(setter.getPropertyActualType())) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.onIfNotNull(var).scriptOf("{}.{}({}.{}({}))", THAT, setter.getMethodName(),

                convertClass, getConvertMethodName(), var);
        } else {
            String var = defineGetterValueVar(scripts, getter);
            onNull(scripts, setter, var).or("{}.{}({})", convertClass, getConvertMethodName(), var);
        }
    }
}
