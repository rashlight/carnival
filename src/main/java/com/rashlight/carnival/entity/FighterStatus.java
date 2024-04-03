package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum FighterStatus implements EnumClass<Integer> {

    PENDING(0),
    WON(1),
    LOST(2);

    private final Integer id;

    FighterStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static FighterStatus fromId(Integer id) {
        for (FighterStatus at : FighterStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}