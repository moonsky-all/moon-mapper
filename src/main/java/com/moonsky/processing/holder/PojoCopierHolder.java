package com.moonsky.processing.holder;

import com.moonsky.mapper.annotation.MapperNaming;
import com.moonsky.processing.declared.PojoDeclared;
import com.moonsky.processing.definition.PojoCopierDefinition;
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
public class PojoCopierHolder extends AbstractHolder implements JavaWritable {

    private final Map<String, PojoCopierDefinition> copierRecordMap = new HashMap<>();

    protected PojoCopierHolder(Holders holders) { super(holders); }

    public PojoCopierDefinition with(MapperNaming naming, TypeElement thisElement, TypeElement thatElement) {
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
        copierRecordMap.forEach((key, copier) -> {
            JavaFiler.write(copier.get());
        });
    }
}
