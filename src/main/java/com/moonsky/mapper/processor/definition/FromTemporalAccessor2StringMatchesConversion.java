package com.moonsky.mapper.processor.definition;

import com.moonsky.processor.processing.declared.PropertyMethodDeclared;
import com.moonsky.processor.processing.generate.CodeMethodBlockAddr;
import com.moonsky.processor.processing.util.String2;

import java.time.*;

import static com.moonsky.mapper.processor.definition.ConversionUtils.*;

/**
 * @author benshaoye
 */
public class FromTemporalAccessor2StringMatchesConversion extends BaseConversion implements Conversion {

    public FromTemporalAccessor2StringMatchesConversion() {}

    @Override
    public void register(ConversionRegistry registry) {
        registry.register(classname(LocalTime.class), CLASS_String, this);
        registry.register(classname(LocalDate.class), CLASS_String, this);
        registry.register(classname(LocalDateTime.class), CLASS_String, this);

        registry.register(classname(OffsetTime.class), CLASS_String, this);
        registry.register(classname(OffsetDateTime.class), CLASS_String, this);

        registry.register(classname(ZonedDateTime.class), CLASS_String, this);
        registry.register(classname(YearMonth.class), CLASS_String, this);
        registry.register(classname(MonthDay.class), CLASS_String, this);
        registry.register(classname(Month.class), CLASS_String, this);

        registry.registerMatches(testerSubtypeOf(CLASS_TemporalAccessor), CLASS_String, this);
    }

    @Override
    public void doMapping(
        CodeMethodBlockAddr scripts, PropertyMethodDeclared getter, PropertyMethodDeclared setter
    ) {
        String pattern = getFormatOrDefaultIfBlank(setter, getter, null);
        if (String2.isBlank(pattern)) {
            String formatConst = defineJdk8DateTimeFormatter(scripts, pattern);
            String var = defineGetterValueVar(scripts, getter);
            onNull(scripts, setter, var).or("{}.format({})", formatConst, var);
        } else {
            onNullOr(scripts, getter, setter, "{}.toString()", AS_ORIGINAL_ARGS);
        }
    }
}
