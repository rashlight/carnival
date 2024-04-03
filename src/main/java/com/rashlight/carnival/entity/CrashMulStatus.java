package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum CrashMulStatus implements EnumClass<Integer> {

    PENDING(0),
    WON(1),
    LOST(2);

    private final Integer id;

    CrashMulStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static CrashMulStatus fromId(Integer id) {
        for (CrashMulStatus at : CrashMulStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}