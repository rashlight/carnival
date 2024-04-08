package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import java.util.UUID;

@JmixEntity
public class CrashMulState {
    private UUID matchId;

    private Boolean isPlayerStop;

    private Double finalMultiplier;

    private Long pointsGiven;

    private Double multiplier;

    public Boolean getIsPlayerStop() {
        return isPlayerStop;
    }

    public void setIsPlayerStop(Boolean isPlayerStop) {
        this.isPlayerStop = isPlayerStop;
    }

    public Long getPointsGiven() {
        return pointsGiven;
    }

    public void setPointsGiven(Long pointsGiven) {
        this.pointsGiven = pointsGiven;
    }

    public UUID getMatchId() {
        return matchId;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setFinalMultiplier(Double finalMultiplier) {
        this.finalMultiplier = finalMultiplier;
    }

    public Double getFinalMultiplier() {
        return finalMultiplier;
    }
}