package com.rashlight.carnival.entity;

import io.jmix.core.Entity;

/**
 * Ensure the View has methods for updating the gameplay data to Session and Result entities
 */
public interface SessionResultUpdate {
    /**
     * Save new Session entity using a State DTO or a Result entity
     */
    void updateSession();

    /**
     * Save new Result entity using a State DTO
     */
    void updateResult();
}