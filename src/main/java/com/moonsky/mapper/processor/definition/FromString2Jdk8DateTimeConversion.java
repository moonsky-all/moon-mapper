package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.Collect2;
import com.moonsky.processor.processing.util.Imported;
import com.moonsky.processor.processing.util.String2;

import java.time.*;
import java.time.temporal.TemporalAccessor;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
public class FromString2Jdk8DateTimeConversion extends BaseConversion implements Conversion {

    public FromString2Jdk8DateTimeConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        for (Class<? extends TemporalAccessor> klass : Collect2.list(ZonedDateTime.class,
            OffsetDateTime.class,
            OffsetTime.class,
            LocalDateTime.class,
            LocalDate.class,
            LocalTime.class,
            YearMonth.class,
            MonthDay.class,
            Year.class,
            Month.class)) {
            registry.register(CLASS_String, classname(klass), this);
        }
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String setterActualType = setter.getPropertyActualType();
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        Imported<?> setterActualImported = Imported.nameOf(setterActualType);
        String var = defineGetterValueVar(scripts, getter);
        if (String2.isBlank(pattern)) {
            String tpl = CLASS_jdk8_Month.equals(setterActualType) ? "{}.valueOf({})" : "{}.parse({})";
            onNull(scripts, setter, var).or(tpl, setterActualImported, var);
        } else {
            String formatConst = defineJdk8DateTimeFormatter(scripts, pattern);
            onNull(scripts, setter, var).or("{}.parse({}, {})", setterActualImported, var, formatConst);
        }
    }
}
