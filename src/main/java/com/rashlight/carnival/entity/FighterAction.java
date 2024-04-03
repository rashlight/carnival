package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum FighterAction implements EnumClass<Integer> {

    UNKNOWN(0),
    ATTACK(1),
    DEFEND(2),
    DASH(3);

    private final Integer id;

    FighterAction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static FighterAction fromId(Integer id) {
        for (FighterAction at : FighterAction.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}