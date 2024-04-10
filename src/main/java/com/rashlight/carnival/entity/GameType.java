package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum GameType implements EnumClass<Integer> {

    SHENANIGANS(-1),
    GUESSNUM(0),
    CRASHMUL(1),
    FIGHTER(2);

    private final Integer id;

    GameType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static GameType fromId(Integer id) {
        for (GameType at : GameType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}