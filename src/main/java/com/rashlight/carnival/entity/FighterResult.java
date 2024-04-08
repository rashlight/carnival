package com.rashlight.carnival.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Table(name = "FIGHTER_RESULT")
@JmixEntity
@Entity
public class FighterResult {
    @Column(name = "ID", nullable = false)
    @Id
    @JmixGeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "MATCH_ID", nullable = false)
    @JmixProperty(mandatory = true)
    private UUID matchId;

    @NotNull
    @Column(name = "FRIENDLY_MATCH_POINT", nullable = false)
    private Integer friendlyMatchPoint;

    @Column(name = "STATUS", nullable = false)
    @JmixProperty(mandatory = true)
    @NotNull
    private Integer status;

    @NotNull
    @Column(name = "ENEMY_MATCH_POINT", nullable = false)
    private Integer enemyMatchPoint;

    @Column(name = "SIDE", nullable = false)
    @JmixProperty(mandatory = true)
    @NotNull
    private Integer side;

    @Column(name = "FRIENDLY_POSITION", nullable = false)
    @Max(13)
    @Min(0)
    @JmixProperty(mandatory = true)
    @NotNull
    private Integer friendlyPosition;

    @Column(name = "ENEMY_POSITION", nullable = false)
    @JmixProperty(mandatory = true)
    @Max(13)
    @Min(0)
    @NotNull
    private Integer enemyPosition;

    @NotNull
    @Column(name = "FRIENDLY_ACTION", nullable = false)
    private Integer friendlyAction;

    @NotNull
    @Column(name = "ENEMYACTION", nullable = false)
    private Integer enemyAction;

    @NotNull
    @Column(name = "FRIENDLY_DELTA", nullable = false)
    private Integer friendlyDelta;

    @Column(name = "ENEMY_DELTA")
    private Integer enemyDelta;

    @Column(name = "POINTSGIVEN", nullable = false)
    @JmixProperty(mandatory = true)
    @NotNull
    private Long pointsGiven;

    public void setEnemyDelta(Integer enemyDelta) {
        this.enemyDelta = enemyDelta;
    }

    public Integer getEnemyDelta() {
        return enemyDelta;
    }

    public void setFriendlyDelta(Integer friendlyDelta) {
        this.friendlyDelta = friendlyDelta;
    }

    public Integer getFriendlyDelta() {
        return friendlyDelta;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }

    public UUID getMatchId() {
        return matchId;
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

    public FighterSide getSide() {
        return side == null ? null : FighterSide.fromId(side);
    }

    public void setSide(FighterSide side) {
        this.side = side == null ? null : side.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}