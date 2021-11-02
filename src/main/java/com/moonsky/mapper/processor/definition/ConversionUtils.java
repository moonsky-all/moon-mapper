package com.moonsky.mapper.processor.definition;

import com.moonsky.mapper.annotation.Mapping;
import com.moonsky.processor.processing.declared.PropertyDeclared;
import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.*;
import com.moonsky.processor.processing.util.*;

import javax.lang.model.element.Modifier;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

/**
 * @author benshaoye
 */
enum ConversionUtils {
    ;
    final static String THIS = "from";
    final static String THAT = "that";

    final static CodeLineNullOrElseHelper<CodeBlockAddr<? extends CodeBlockAddr<ElemMethod>>> onNull(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, String var
    ) {return scripts.onNullOrVerify(THAT, setter.getMethodName(), var);}

    @SuppressWarnings("all")
    final static void setValueOf(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, String template, Object... values
    ) {
        scripts.onPresetOf("{}.{}({}.valueOf({}))", THAT, setter.getMethodName(),//
            Imported.nameOf(setter.getPropertyActualType())).of(template, values);
    }

    final static String doGetterVal(PropertyMethodDeclared getter) {
        return String2.format("{}.{}()", THIS, getter.getMethodName());
    }

    final static String doGetterVal(PropertyMethodDeclared getter, String cast) {
        return String2.isBlank(cast) ? doGetterVal(getter) : (cast + doGetterVal(getter));
    }

    static void doNoneMapping(
        CodeMethodBlockAddr scripts, PropertyDeclared thatProperty, PropertyDeclared thisProperty
    ) {
        final String codeKey = UUID.randomUUID().toString();
        scripts.keyScriptOf(codeKey,
            "/** Can't mapping '{@link {}#{}}({@link {}})' to '{@link {}#{}}({@link {}})' */",
            thisProperty.getThisSimpleName(),
            thisProperty.getName(),
            thisProperty.getPropertyType(),
            thatProperty.getThisSimpleName(),
            thatProperty.getName(),
            thatProperty.getPropertyType()).unEndCapable(codeKey);
    }

    static String defineGetterValueVar(CodeMethodBlockAddr scripts, PropertyMethodDeclared getter) {
        String var = String2.toPropertyName(getter.getMethodName());
        String getterActualType = getter.getPropertyActualType();
        scripts.scriptOf("{} {} = {}.{}()", Imported.nameOf(getterActualType), var, THIS, getter.getMethodName());
        return var;
    }

    static String defineEnumValues(CodeMethodBlockAddr scripts, String enumClassname) {
        VarSupplier<ElemField> fieldsSupplier = scripts.fieldsHelper();
        String constVar = fieldsSupplier.nextConstVar(enumClassname);
        if (fieldsSupplier.contains(constVar)) {
            return constVar;
        }
        fieldsSupplier.declareField(constVar, "{}[]", enumClassname)
            .assign()
            .valueOfFormatted("{}.values()", Imported.nameOf(enumClassname))
            .end()
            .modifierWithAll(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        return constVar;
    }

    static String defineJodaDateTimeFormatter(CodeMethodBlockAddr scripts, String pattern) {
        VarSupplier<ElemField> fieldsSupplier = scripts.fieldsHelper();
        String formatterName = JodaClassnames.CLASS_Joda_DateTimeFormat;
        String patternKey = String2.keyOf(formatterName, pattern);
        String constVar = fieldsSupplier.nextConstVar(patternKey);
        if (fieldsSupplier.contains(constVar)) {
            return constVar;
        }
        fieldsSupplier.declareField(constVar, JodaClassnames.CLASS_Joda_DateTimeFormatter)
            .assign()
            .valueOfFormatted("{}.forPattern({})", Imported.nameOf(formatterName), Stringify.of(pattern))
            .end()
            .modifierWithAll(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        return constVar;
    }

    static String defineJdk8DateTimeFormatter(CodeMethodBlockAddr scripts, String pattern) {
        VarSupplier<ElemField> fieldsSupplier = scripts.fieldsHelper();
        String formatterName = DateTimeFormatter.class.getCanonicalName();
        String patternKey = String2.keyOf(formatterName, pattern);
        String constVar = fieldsSupplier.nextConstVar(patternKey);
        if (fieldsSupplier.contains(constVar)) {
            return constVar;
        }
        fieldsSupplier.declareField(constVar, formatterName)
            .assign()
            .valueOfFormatted("{}.ofPattern({})", Imported.nameOf(formatterName), Stringify.of(pattern))
            .end()
            .modifierWithAll(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        return constVar;
    }

    static String getFormatOrDefaultIfBlank(
        PropertyMethodDeclared setter, PropertyMethodDeclared getter, String defaultPattern
    ) {
        Mapping mapping = getFormatDefinition(setter, getter);
        return mapping == null ? defaultPattern : String2.defaultIfBlank(mapping.format(), defaultPattern);
    }

    static Mapping getFormatDefinition(PropertyMethodDeclared setter, PropertyMethodDeclared getter) {
        Mapping[] formats = getter.getMethodAnnotations(Mapping.class);
        if (formats == null || formats.length == 0) {
            formats = getter.getFieldAnnotations(Mapping.class);
        }
        if (formats == null || formats.length == 0) {
            formats = setter.getMethodAnnotations(Mapping.class);
        }
        if (formats == null || formats.length == 0) {
            formats = setter.getFieldAnnotations(Mapping.class);
        }
        return formats == null || formats.length == 0 ? null : formats[0];
    }
}
