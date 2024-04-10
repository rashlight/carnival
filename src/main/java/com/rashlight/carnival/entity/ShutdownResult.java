package com.rashlight.carnival.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "SHUTDOWN_RESULT", indexes = {
        @Index(name = "IDX_SHUTDOWN_RESULT_USER", columnList = "USER_ID")
})
@Entity
public class ShutdownResult {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "MATCH_ID", nullable = false)
    private UUID matchId;

    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(name = "TIME", nullable = false)
    @NotNull
    private LocalDateTime time;

    @Column(name = "POINTS_GIVEN", nullable = false)
    @NotNull
    private Long pointsGiven;

    @Column(name = "MULTIPLIER", nullable = false)
    @NotNull
    private Double multiplier;

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }

    public UUID getMatchId() {
        return matchId;
    }

    public Long getPointsGiven() {
        return pointsGiven;
    }

    public void setPointsGiven(Long pointsGiven) {
        this.pointsGiven = pointsGiven;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}