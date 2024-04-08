package com.rashlight.carnival.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "CRASH_MUL_RESULT", indexes = {
        @Index(name = "IDX_CRASH_MUL_RESULT_USER", columnList = "USER_ID")
})
@JmixEntity
@Entity
public class CrashMulResult {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "MATCH_ID", nullable = false)
    private UUID matchId;

    @NotNull
    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(name = "TIME_")
    private LocalDateTime time;

    @NotNull
    @Column(name = "POINTS_GIVEN", nullable = false)
    private Long pointsGiven;

    @NotNull
    @Column(name = "IS_PLAYER_STOP", nullable = false)
    private Boolean isPlayerStop = false;

    @NotNull
    @Column(name = "FINAL_MULTIPLIER", nullable = false)
    private Double finalMultiplier;

    @NotNull
    @Column(name = "MULTIPLIER", nullable = false)
    private Double multiplier;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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