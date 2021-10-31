package com.moonsky.mapper.processor.holder;

import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.mapper.processor.definition.PojoCopierDefinition;
import com.moonsky.mapper.processor.definition.PojoMapperDefinition;
import com.moonsky.processor.processing.filer.JavaFiler;
import com.moonsky.processor.processing.filer.JavaWritable;
import com.moonsky.processor.processing.util.Element2;
import com.moonsky.processor.processing.util.String2;

import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public class PojoMapperHolder extends AbstractHolder implements JavaWritable {

    private final Map<String, PojoMapperDefinition> mapperRecordMap = new HashMap<>();

    public PojoMapperHolder(MapperHolders holders) {super(holders);}

    public PojoMapperDefinition with(
        MapperNaming namingAttributes, TypeElement thisElement, TypeElement thatElement
    ) {
        String thisName = Element2.getQualifiedName(thisElement);
        String thatName = Element2.getQualifiedName(thatElement);
        String mappedKey = String2.keyOf(thisName, thatName);
        PojoMapperDefinition record = mapperRecordMap.get(mappedKey);
        if (record == null) {
            PojoCopierDefinition forward = pojoCopierHolder().with(thisElement, thatElement);
            PojoCopierDefinition backward = pojoCopierHolder().with(thatElement, thisElement);
            record = new PojoMapperDefinition(getHolders(), namingAttributes, forward, backward);
            mapperRecordMap.put(mappedKey, record);
        }
        return record;
    }

    @Override
    public void write() {
        JavaFiler.writeAll(mapperRecordMap);
    }
}
