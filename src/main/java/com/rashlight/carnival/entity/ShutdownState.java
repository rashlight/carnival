package com.rashlight.carnival.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
public class ShutdownState extends State {

    @JmixProperty(mandatory = true)
    @NotNull
    private UUID matchId;

    @JmixProperty(mandatory = true)
    @NotNull
    private User user;

    @JmixProperty(mandatory = true)
    @NotNull
    private Long pointsGiven;

    @JmixProperty(mandatory = true)
    @NotNull
    private Double multiplier;

    @JmixProperty(mandatory = true)
    @NotNull
    private LocalDateTime time;

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

    public UUID getMatchId() {
        return matchId;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
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

}