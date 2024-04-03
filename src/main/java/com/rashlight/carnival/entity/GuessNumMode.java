package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum GuessNumMode implements EnumClass<Integer> {

    PRESTART(0),
    POSTSTART(1);

    private final Integer id;

    GuessNumMode(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static GuessNumMode fromId(Integer id) {
        for (GuessNumMode at : GuessNumMode.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}