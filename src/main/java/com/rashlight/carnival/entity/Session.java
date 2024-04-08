package com.rashlight.carnival.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "SESSION_", indexes = {
        @Index(name = "IDX_SESSION__USER", columnList = "USER_ID")
})
@Entity(name = "Session_")
public class Session {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(name = "TIME", nullable = false)
    @NotNull
    private LocalDateTime time;

    @NotNull
    @Column(name = "GAME_TYPE", nullable = false)
    private Integer gameType;

    @Column(name = "MATCH_ID", nullable = false)
    @NotNull
    private UUID matchId;

    @NotNull
    @Column(name = "POINTS_CHANGE", nullable = false)
    private Long pointsChange;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType == null ? null : gameType.getId();
    }

    public GameType getGameType() {
        return gameType == null ? null : GameType.fromId(gameType);
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}