package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
public class GuessNumState extends State {

    @NotNull
    private UUID matchId;

    private Long pointsGiven;

    @NotNull
    @JmixProperty(mandatory = true)
    private Double multiplier;

    private Integer attemptValue;

    @Max(100)
    @Min(0)
    private Integer actualNum;

    private Integer attemptsLeft;

    public Integer getAttemptValue() {
        return attemptValue;
    }

    public void setAttemptValue(Integer attemptValue) {
        this.attemptValue = attemptValue;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public Long getPointsGiven() {
        return pointsGiven;
    }

    public void setPointsGiven(Long pointsGiven) {
        this.pointsGiven = pointsGiven;
    }

    public void setActualNum(Integer actualNum) {
        this.actualNum = actualNum;
    }

    public Integer getActualNum() {
        return actualNum;
    }

    public Integer getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(Integer attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public void decrementAttempts() {
        setActualNum(Math.clamp(attemptsLeft - 1, 0, 5));
    }

    public UUID getMatchId() {
        return matchId;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }
}