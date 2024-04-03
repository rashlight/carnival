package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import java.util.UUID;

@JmixEntity
public class CrashMulState {
    private UUID matchId;

    private Double playerMultiplier;

    private Double finalMultiplier;

    private Long pointsGiven;

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

    public void setFinalMultiplier(Double finalMultiplier) {
        this.finalMultiplier = finalMultiplier;
    }

    public Double getFinalMultiplier() {
        return finalMultiplier;
    }

    public void setPlayerMultiplier(Double playerMultiplier) {
        this.playerMultiplier = playerMultiplier;
    }

    public Double getPlayerMultiplier() {
        return playerMultiplier;
    }
}