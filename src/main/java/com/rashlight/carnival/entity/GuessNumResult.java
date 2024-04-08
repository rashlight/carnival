package com.rashlight.carnival.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "GUESS_NUM_RESULT", indexes = {
        @Index(name = "IDX_GUESS_NUM_RESULT_USER", columnList = "USER_ID")
})
@JmixEntity
@Entity
public class GuessNumResult {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "MATCH_ID", nullable = false)
    @NotNull
    private UUID matchID;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull
    private User user;

    @NotNull
    @Max(5)
    @Min(0)
    @Column(name = "ATTEMPT", nullable = false)
    private Integer attempt;

    @Max(100)
    @Min(0)
    @Column(name = "ATTEMPT_VALUE", nullable = false)
    @NotNull
    private Integer attemptValue;

    @NotNull
    @Max(100)
    @Min(0)
    @Column(name = "ACTUAL_VALUE", nullable = false)
    private Integer actualValue;

    @Column(name = "MULTIPLIER")
    private Double multiplier;

    @Min(0)
    @NotNull
    @Column(name = "POINTS_GIVEN", nullable = false)
    private Long pointsGiven;

    @Column(name = "TIME", nullable = false)
    @NotNull
    private LocalDateTime time;

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public UUID getMatchID() {
        return matchID;
    }

    public void setMatchID(UUID matchID) {
        this.matchID = matchID;
    }

    public void setPointsGiven(Long pointsGiven) {
        this.pointsGiven = pointsGiven;
    }

    public Long getPointsGiven() {
        return pointsGiven;
    }

    public void setActualValue(Integer actualValue) {
        this.actualValue = actualValue;
    }

    public Integer getActualValue() {
        return actualValue;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public Integer getAttemptValue() {
        return attemptValue;
    }

    public void setAttemptValue(Integer attemptValue) {
        this.attemptValue = attemptValue;
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