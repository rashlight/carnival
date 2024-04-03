package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum FighterJudgement implements EnumClass<String> {

    ;

    private final String id;

    FighterJudgement(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static FighterJudgement fromId(String id) {
        for (FighterJudgement at : FighterJudgement.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}