package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum GuessNumJudgement implements EnumClass<Integer> {

    EXACT(0),
    LOWER(1),
    HIGHER(2);

    private final Integer id;

    GuessNumJudgement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static GuessNumJudgement fromId(Integer id) {
        for (GuessNumJudgement at : GuessNumJudgement.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}