package com.rashlight.carnival.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "CRASH_MUL_RESULT")
@JmixEntity
@Entity
public class CrashMulResult {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "MATCH_ID")
    private UUID matchId;

    @Column(name = "IS_PLAYER_STOP")
    private Boolean isPlayerStop;

    @Column(name = "FINAL_MULTIPLIER")
    private Double finalMultiplier;

    @Column(name = "POINTS_GIVEN")
    private Long pointsGiven;

    @Column(name = "MULTIPLIER")
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

    public Double getFinalMultiplier() {
        return finalMultiplier;
    }

    public void setFinalMultiplier(Double finalMultiplier) {
        this.finalMultiplier = finalMultiplier;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }

    public UUID getMatchId() {
        return matchId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}