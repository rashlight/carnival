package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum FighterMode implements EnumClass<Integer> {

    PRESTART(0),
    POSTSTART(1);

    private final Integer id;

    FighterMode(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static FighterMode fromId(Integer id) {
        for (FighterMode at : FighterMode.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}