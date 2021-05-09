package com.moonsky.processing.holder;

/**
 * @author benshaoye
 */
public class MapperHolder extends AbstractHolder {

    protected MapperHolder(Holders holders) { super(holders); }

    private String toMapperKey(String thisClass, String thatClass) {
        return String.format("%s>%s", thisClass, thatClass);
    }
}
