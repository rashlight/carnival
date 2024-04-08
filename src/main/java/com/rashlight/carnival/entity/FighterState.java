package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
public class FighterState extends State {

    @JmixProperty(mandatory = true)
    @NotNull
    private UUID matchId;

    @JmixProperty(mandatory = true)
    @NotNull
    private Integer status;

    private Integer friendlyMatchPoint;

    private Integer enemyMatchPoint;

    @JmixProperty(mandatory = true)
    @NotNull
    private Integer friendlySide;

    @JmixProperty(mandatory = true)
    @NotNull
    private Integer enemySide;

    @Max(11)
    @Min(-2)
    @JmixProperty(mandatory = true)
    @NotNull
    private Integer friendlyPosition;

    @JmixProperty(mandatory = true)
    @Max(11)
    @Min(-2)
    @NotNull
    private Integer enemyPosition;

    private Integer friendlyAction;

    private Integer enemyAction;

    private Integer friendlyDelta;

    private Integer enemyDelta;

    @JmixProperty(mandatory = true)
    @NotNull
    private Long pointsGiven;

    private Double multiplier;

    public Integer getEnemyDelta() {
        return enemyDelta;
    }

    public void setEnemyDelta(Integer enemyDelta) {
        this.enemyDelta = enemyDelta;
    }

    public Integer getFriendlyDelta() {
        return friendlyDelta;
    }

    public void setFriendlyDelta(Integer friendlyDelta) {
        this.friendlyDelta = friendlyDelta;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public FighterSide getEnemySide() {
        return enemySide == null ? null : FighterSide.fromId(enemySide);
    }

    public void setEnemySide(FighterSide enemySide) {
        this.enemySide = enemySide == null ? null : enemySide.getId();
    }

    public void setStatus(FighterStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public FighterStatus getStatus() {
        return status == null ? null : FighterStatus.fromId(status);
    }

    public void setEnemyMatchPoint(Integer enemyMatchPoint) {
        this.enemyMatchPoint = enemyMatchPoint;
    }

    public Integer getEnemyMatchPoint() {
        return enemyMatchPoint;
    }

    public void setFriendlyMatchPoint(Integer friendlyMatchPoint) {
        this.friendlyMatchPoint = friendlyMatchPoint;
    }

    public Integer getFriendlyMatchPoint() {
        return friendlyMatchPoint;
    }

    public void setPointsGiven(Long pointsGiven) {
        this.pointsGiven = pointsGiven;
    }

    public Long getPointsGiven() {
        return pointsGiven;
    }

    public void setEnemyPosition(Integer enemyPosition) {
        this.enemyPosition = enemyPosition;
    }

    public Integer getEnemyPosition() {
        return enemyPosition;
    }

    public FighterAction getEnemyAction() {
        return enemyAction == null ? null : FighterAction.fromId(enemyAction);
    }

    public void setEnemyAction(FighterAction enemyAction) {
        this.enemyAction = enemyAction == null ? null : enemyAction.getId();
    }

    public FighterAction getFriendlyAction() {
        return friendlyAction == null ? null : FighterAction.fromId(friendlyAction);
    }

    public void setFriendlyAction(FighterAction friendlyAction) {
        this.friendlyAction = friendlyAction == null ? null : friendlyAction.getId();
    }

    public Integer getFriendlyPosition() {
        return friendlyPosition;
    }

    public void setFriendlyPosition(Integer friendlyPosition) {
        this.friendlyPosition = friendlyPosition;
    }

    public FighterSide getFriendlySide() {
        return friendlySide == null ? null : FighterSide.fromId(friendlySide);
    }

    public void setFriendlySide(FighterSide friendlySide) {
        this.friendlySide = friendlySide == null ? null : friendlySide.getId();
    }

    public UUID getMatchId() {
        return matchId;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }
}