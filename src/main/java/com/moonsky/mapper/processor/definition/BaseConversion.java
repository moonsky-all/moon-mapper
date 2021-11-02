package com.moonsky.mapper.processor.definition;

import com.moonsky.mapper.util.Formatter;
import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Import2;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.JodaClassnames;
import com.moonsky.processor.processing.util.Test2;

import java.util.function.Function;
import java.util.function.Predicate;

import static com.moonsky.mapper.processor.definition.ConversionUtils.defineGetterValueVar;
import static com.moonsky.mapper.processor.definition.ConversionUtils.onNull;

/**
 * @author benshaoye
 */
public abstract class BaseConversion extends JodaClassnames implements Conversion {

    protected static final Imported<Class<Formatter>> FORMATTER_IMPORTED = Imported.of(Formatter.class);

    protected final static Predicate<String> NUMBER_TESTER = testerSubtypeOf(CLASS_Number);

    protected static final Function<String, Object[]> AS_ORIGINAL_ARGS = var -> objectsOf(var);
    protected final static Function<String, Object[]> AS_BIG_DECIMAL__VAR;
    protected final static Function<String, Object[]> AS_BIG_INTEGER__VAR;

    static {
        AS_BIG_DECIMAL__VAR = var -> objectsOf(Imported.BIG_DECIMAL, var);
        AS_BIG_INTEGER__VAR = var -> objectsOf(Imported.BIG_INTEGER, var);
    }

    public BaseConversion() {}

    protected static final void onNullOr(
        CodeMethodBlockAddr scripts,
        PropertyMethodDeclared getter,
        PropertyMethodDeclared setter,
        String template,
        Function<String, Object[]> argsBuilder
    ) {
        String var = defineGetterValueVar(scripts, getter);
        onNull(scripts, setter, var).or(template, argsBuilder.apply(var));
    }

    protected static final Object[] objectsOf(Object... values) {return values;}

    protected static final Predicate<String> testerSubtypeOf(String targetSuperclass) {
        return getterActualType -> Test2.isSubtypeOf(getterActualType, targetSuperclass);
    }

    protected static final boolean isNotImported2x() {
        return !IMPORTED_Joda2x0;
    }

    protected static final boolean isNotImported1x4() {
        return !IMPORTED_Joda1x4;
    }

    protected static final boolean isNotImported1x3() {
        return !IMPORTED_Joda1x3;
    }

    protected static final boolean isNotImported1x0() {
        return !IMPORTED_Joda1x0;
    }
}
