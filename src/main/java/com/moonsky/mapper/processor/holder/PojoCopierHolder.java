package com.moonsky.mapper.processor.holder;

import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.mapper.processor.definition.PojoCopierDefinition;
import com.moonsky.mapper.util.DefaultNaming;
import com.moonsky.processor.processing.declared.PojoDeclared;
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
public class PojoCopierHolder extends AbstractHolder implements JavaWritable {

    private final Map<String, PojoCopierDefinition> copierRecordMap = new HashMap<>();

    protected PojoCopierHolder(MapperHolders holders) {super(holders);}

    public PojoCopierDefinition with(TypeElement thisElement, TypeElement thatElement) {
        MapperNaming naming = DefaultNaming.defaultIfNull(thisElement.getAnnotation(MapperNaming.class));
        String thisName = Element2.getQualifiedName(thisElement);
        String thatName = Element2.getQualifiedName(thatElement);
        String mappedKey = String2.keyOf(thisName, thatName);
        PojoCopierDefinition record = copierRecordMap.get(mappedKey);
        if (record == null) {
            PojoDeclared thisDeclared = pojoClassHolder().with(thisElement);
            PojoDeclared thatDeclared = pojoClassHolder().with(thatElement);
            record = new PojoCopierDefinition(getHolders(), naming, thisDeclared, thatDeclared);
            copierRecordMap.put(mappedKey, record);
        }
        return record;
    }

    @Override
    public void write() {
        JavaFiler.writeAll(copierRecordMap);
    }
}
