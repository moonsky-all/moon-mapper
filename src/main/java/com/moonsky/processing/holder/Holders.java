package com.moonsky.processing.holder;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public enum Holders {
    /** default instance */
    INSTANCE;

    private final ClassHolder typeHolder = new ClassHolder(this);
    private final PojoClassHolder pojoHolder = new PojoClassHolder(this);
    private final PojoCopierHolder copierHolder = new PojoCopierHolder(this);
    private final PojoMapperHolder mapperHolder = new PojoMapperHolder(this);

    @SuppressWarnings("all")
    private final ThreadLocal<ProcessingEnvironment> ENV = new ThreadLocal<>();
    private final Map<String, TypeMirror> basicTypes = new HashMap<>();

    private final LazyHolder<Elements> elementsHolder = LazyHolder.of(() -> ENV.get().getElementUtils());
    private final LazyHolder<Types> typesHolder = LazyHolder.of(() -> ENV.get().getTypeUtils());
    private final LazyHolder<Messager> messageHolder = LazyHolder.of(() -> ENV.get().getMessager());

    public void init(ProcessingEnvironment environment) {
        ENV.set(environment);
        Types typesUtil = getTypes();
        Map<String, TypeMirror> basicTypesDef = this.basicTypes;
        for (TypeKind value : TypeKind.values()) {
            if (value.isPrimitive()) {
                TypeMirror type = typesUtil.getPrimitiveType(value);
                basicTypesDef.put(type.toString(), type);
            } else if (value == TypeKind.VOID) {
                TypeMirror type = typesUtil.getNoType(value);
                basicTypesDef.put(type.toString(), type);
            } else if (value == TypeKind.NULL) {
                TypeMirror type = typesUtil.getNullType();
                basicTypesDef.put(type.toString(), type);
                basicTypesDef.put("null", type);
            }
        }
    }

    public ProcessingEnvironment getEnvironment() { return ENV.get(); }

    public Messager getMessager() { return messageHolder.get(); }

    public Elements getUtils() { return elementsHolder.get(); }

    public Types getTypes() { return typesHolder.get(); }

    public TypeMirror getBasicTypeMirror(String typeName) {
        return basicTypes.get(typeName.toLowerCase());
    }

    public ClassHolder classHolder() { return typeHolder; }

    public PojoCopierHolder pojoCopierHolder() { return copierHolder; }

    public PojoMapperHolder pojoMapperHolder() { return mapperHolder; }

    public PojoClassHolder pojoClassHolder() { return pojoHolder; }
}
