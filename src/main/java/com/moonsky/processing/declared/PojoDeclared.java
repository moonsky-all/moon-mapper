package com.moonsky.processing.declared;

import com.moonsky.mapper.annotation.Mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author benshaoye
 */
public class PojoDeclared extends BaseDeclarable {

    private final ClassDeclared classDeclared;
    private final Map<String, String> getterPropertyMapped = new HashMap<>();
    private final Map<String, String> setterPropertyMapped = new HashMap<>();

    PojoDeclared(ClassDeclared classDeclared) {
        super(classDeclared.getHolders(),
            classDeclared.getThisElement(),
            classDeclared.getEnclosingElement(),
            classDeclared.getThisGenericsMap());
        this.classDeclared = classDeclared;
        classDeclared.getProperties().forEach((name, prop) -> {
            PropertyMethodDeclared getter = prop.getOriginGetterDeclared();
            PropertyMethodDeclared setter = prop.getOriginSetterDeclared();
            PropertyFieldDeclared field = prop.getOriginFieldDeclared();

            Mapping[] getterMaps = loadMappings(getter, field);
            Mapping[] setterMaps = loadMappings(setter, field);
        });
    }

    public static PojoDeclared from(ClassDeclared classDeclared) {
        return new PojoHelper(classDeclared).toPojoDeclared();
    }

    public ClassDeclared getClassDeclared() { return classDeclared; }

    private static Mapping[] loadMappings(PropertyMethodDeclared method, PropertyFieldDeclared field) {
        Mapping[] mappings = method.getAnnotations(Mapping.class);
        if (mappings == null || mappings.length == 0) {
            return field.getAnnotations(Mapping.class);
        }
        return mappings;
    }
}
