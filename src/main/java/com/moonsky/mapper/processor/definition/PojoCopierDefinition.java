package com.moonsky.mapper.processor.definition;

import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.mapper.processor.holder.MapperHolders;
import com.moonsky.mapper.util.Formatter;
import com.moonsky.mapper.util.NamingStrategy;
import com.moonsky.processor.processing.declared.PojoDeclared;
import com.moonsky.processor.processing.declared.PropertyDeclared;
import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.filer.JavaFileDetails;
import com.moonsky.processor.processing.filer.JavaSupplier;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.generate.ElemMethod;
import com.moonsky.processor.processing.generate.JavaFileClassDetails;
import com.moonsky.processor.processing.util.*;

import javax.lang.model.element.Modifier;
import java.util.Map;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
public class PojoCopierDefinition extends PojoBaseDefinition implements JavaSupplier {

    private static final Imported<Class<Formatter>> FORMATTER_IMPORTED = Imported.of(Formatter.class);

    private final String simpleName;
    private final String classname;

    public PojoCopierDefinition(
        MapperHolders holders, MapperNaming namingAttributes, PojoDeclared thisDeclared, PojoDeclared thatDeclared
    ) {
        super(holders, thisDeclared, thatDeclared);
        String thisClass = getThisDeclared().getThisSimpleName();
        String thatClass = getThatDeclared().getThisSimpleName();
        this.simpleName = NamingStrategy.copierOf(namingAttributes, thisClass, thatClass);
        this.classname = getPackageName() + '.' + simpleName;
    }

    public String getClassname() {return classname;}

    public String getSimpleName() {return simpleName;}

    @Override
    public JavaFileDetails get() {
        String packageName = getPackageName();
        String classname = getSimpleName();
        JavaFileClassDetails definition = new JavaFileClassDetails(packageName, classname);
        definition.impls()
            .implement("{}<{}, {}>", AliasConstant2.BeanCopier_ClassName, getThisClassname(), getThatClassname());
        definition.annotateComponent().annotateCopierImplGenerated();

        definition.fields()
            .declareField(AliasConstant2.CONST, definition.getClassname())
            .assign()
            .valueOfFormatted("new {}()", Imported.nameOf(definition.getClassname()))
            .end()
            .modifierWithAll(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        doConvert(definition.methods().declareMethod("convert", param -> {
            param.add(THIS, getThisClassname());
        }).typeOf(getThatClassname()).scripts()).annotateOverride();

        doUnsafeCopy(definition.methods().declareMethod("unsafeCopy", param -> {
            param.add(THIS, getThisClassname());
            param.add(THAT, getThatClassname());
        }).typeOf(getThatClassname()).scripts().returning(THAT)).annotateOverride();

        return definition;
    }

    private ElemMethod doConvert(CodeMethodBlockAddr scripts) {
        return scripts.returning("{} == null ? null : unsafeCopy({}, new {}())",

            THIS, THIS, Imported.nameOf(getThatClassname())).end();
    }

    private ElemMethod doUnsafeCopy(CodeMethodBlockAddr scripts) {
        Map<String, PropertyDeclared> thisProperties = getThisDeclared().getProperties();
        Map<String, PropertyDeclared> thatProperties = getThatDeclared().getProperties();

        for (Map.Entry<String, PropertyDeclared> propertyEntry : thisProperties.entrySet()) {
            PropertyDeclared thatProperty = thatProperties.get(propertyEntry.getKey());
            if (thatProperty == null || !thatProperty.isMaybeWritable()) {
                // 没有 setter
                continue;
            }
            PropertyDeclared thisProperty = propertyEntry.getValue();
            if (!thisProperty.isReadable()) {
                // 没有 getter
                continue;
            }

            String thisPropertyType = thisProperty.getPropertyType();
            String getterRef = thisProperty.getRefGetterMethod(THIS);
            String setterScript = thatProperty.getRefTypeFulledSetterMethod(THAT, getterRef, thisPropertyType);

            // 基于自定义 setter 的转换器，包括同类型直接映射
            if (String2.isNotBlank(setterScript)) {
                scripts.scriptOf(setterScript);
                continue;
            } else if (thatProperty.isMaybeWritable()) {

            }

            // TODO: 2021/11/1 多 setter 的情况下支持子类兼容性的映射
            PropertyMethodDeclared setter = thatProperty.getOnlySetterMethod();
            PropertyMethodDeclared getter = thisProperty.getOriginGetterDeclared();
            if (getter == null || setter == null) {
                // 没有 getter 或 setter
                continue;
            }
            if (doMappingOnConversion(scripts, setter, getter)) {
                // 使用转换器
                continue;
            }
            if (doMappingUnsafeCopy(scripts, setter, getter)) {
                // 默认规则
                continue;
            }
            // 无法映射
            doNoneMapping(scripts, thatProperty, thisProperty);
        }
        return scripts.end();
    }

    private boolean doMappingUnsafeCopy(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();
        if (Test2.isEnumClass(setterActualType)) {
            return doMappingToEnum(scripts, setter, getter);
        } else if (Test2.isSubtypeOf(getterActualType, setterActualType)) {
            scripts.scriptOf("{}.{}({}.{}())", THAT, setter.getMethodName(), THIS, getter.getMethodName());
            return true;
        } else {
            return false;
        }
    }

    private boolean doMappingToEnum(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String getterActualType = getter.getPropertyActualType();

        if (Test2.isTypeof(getterActualType, Classnames.CLASS_String)) {
            String var = defineGetterValueVar(scripts, getter);
            scripts.scriptOf("{}.{}({} == null ? null : {}.valueOf({}))", THAT,

                setter.getMethodName(), var, Imported.nameOf(setterActualType), var);
        } else if (Test2.isPrimitiveNumberClass(getterActualType)) {
            String constVar = defineEnumValues(scripts, setterActualType);
            if (Test2.isSubtypeOfLong(getterActualType)) {
                scripts.scriptOf("{}.{}({}[{}.{}()])", THAT,

                    setter.getMethodName(), constVar, THIS, getter.getMethodName());
            } else {
                scripts.scriptOf("{}.{}({}[(int) {}.{}()])", THAT,

                    setter.getMethodName(), constVar, THIS, getter.getMethodName());
            }
        } else if (Test2.isSubtypeOf(getterActualType, Classnames.CLASS_Number)) {
            String getterVar = defineGetterValueVar(scripts, getter);
            String constVar = defineEnumValues(scripts, setterActualType);
            String getterPrimitiveCls = Classnames.findPrimitiveClass(getterActualType);
            boolean isSubtypeOfLong = getterPrimitiveCls == null || Test2.isSubtypeOfLong(getterPrimitiveCls);
            String valueTemplate = isSubtypeOfLong ? "{}[{}]" : "{}[{}.intValue()]";
            onNull(scripts, setter, getterVar).or(valueTemplate, constVar, getterVar);
        } else if (Test2.isSubtypeOf(getterActualType, Classnames.CLASS_CharSequence)) {
            String var = defineGetterValueVar(scripts, getter);
            onNull(scripts, setter, var).or("{}.valueOf({}.toString())",
                FORMATTER_IMPORTED,
                Imported.nameOf(setterActualType),
                var);
        } else {
            return false;
        }
        return true;
    }

    private static boolean doMappingOnConversion(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared setter, PropertyMethodDeclared getter
    ) {
        Conversion conversion = ConversionRegistry.find(getter.getPropertyActualType(), setter.getPropertyActualType());
        if (conversion != null) {
            conversion.doMapping(scripts, getter, setter);
            return true;
        }
        return false;
    }
}
