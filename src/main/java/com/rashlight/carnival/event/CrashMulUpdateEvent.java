package com.rashlight.carnival.event;

import java.util.EventObject;

public class CrashMulUpdateEvent {
    private final double progress;
    private final boolean failed;

    public CrashMulUpdateEvent(double progress, boolean failed) {
        this.progress = progress;
        this.failed = failed;
    }

    public double getProgress() {
        return progress;
    }

    public boolean getFailed() {
        return failed;
    }
}
