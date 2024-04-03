package com.rashlight.carnival.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "SESSION_")
@Entity(name = "Session_")
public class Session {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "GAME_ID", nullable = false)
    private UUID gameId;

    @Column(name = "MATCH_ID", nullable = false)
    @NotNull
    private UUID matchId;

    @NotNull
    @Column(name = "GAME_RESULT_ID", nullable = false)
    private UUID gameResultId;

    @NotNull
    @Column(name = "POINTS_CHANGE", nullable = false)
    private Long pointsChange;

    @Column(name = "TIME", nullable = false)
    @NotNull
    private LocalDateTime time;

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
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

    public Long getPointsChange() {
        return pointsChange;
    }

    public void setPointsChange(Long pointsChange) {
        this.pointsChange = pointsChange;
    }

    public void setGameResultId(UUID gameResultId) {
        this.gameResultId = gameResultId;
    }

    public UUID getGameResultId() {
        return gameResultId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}