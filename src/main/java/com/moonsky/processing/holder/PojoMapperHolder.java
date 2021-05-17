package com.moonsky.processing.holder;

import com.moonsky.mapper.annotation.MapperFor;
import com.moonsky.processing.definition.PojoCopierDefinition;
import com.moonsky.processing.definition.PojoMapperDefinition;
import com.moonsky.processing.processor.JavaFiler;
import com.moonsky.processing.processor.JavaWritable;
import com.moonsky.processing.util.Element2;
import com.moonsky.processing.util.String2;

import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public class PojoMapperHolder extends AbstractHolder implements JavaWritable {

    private final Map<String, PojoMapperDefinition> mapperRecordMap = new HashMap<>();

    public PojoMapperHolder(Holders holders) { super(holders); }

    public PojoMapperDefinition with(MapperFor mapperFor, TypeElement thisElement, TypeElement thatElement) {
        String thisName = Element2.getQualifiedName(thisElement);
        String thatName = Element2.getQualifiedName(thatElement);
        String mappedKey = String2.keyOf(thisName, thatName);
        PojoMapperDefinition record = mapperRecordMap.get(mappedKey);
        if (record == null) {
            PojoCopierDefinition forward = copierOf(mapperFor, thisElement, thatElement);
            PojoCopierDefinition backward = copierOf(mapperFor, thatElement, thisElement);
            record = new PojoMapperDefinition(getHolders(), mapperFor, forward, backward);
            mapperRecordMap.put(mappedKey, record);
        }
        return record;
    }

    private PojoCopierDefinition copierOf(MapperFor mapperFor, TypeElement thisElement, TypeElement thatElement) {
        return pojoCopierHolder().with(mapperFor, thisElement, thatElement);
    }

    @Override
    public void write() {
        mapperRecordMap.forEach((key, mapper) -> {
            JavaFiler.write(mapper.get());
        });
    }
}
