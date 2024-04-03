package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum FighterSide implements EnumClass<Integer> {

    LEFT(0),
    RIGHT(1);

    private final Integer id;

    FighterSide(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static FighterSide fromId(Integer id) {
        for (FighterSide at : FighterSide.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}