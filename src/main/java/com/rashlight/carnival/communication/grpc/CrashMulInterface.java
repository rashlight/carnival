package com.rashlight.carnival.communication.grpc;

public interface CrashMulInterface {
    double getCurrentMultiplier();
    void initiate();
    boolean bump();
    int getCurrentBumpTime();
}