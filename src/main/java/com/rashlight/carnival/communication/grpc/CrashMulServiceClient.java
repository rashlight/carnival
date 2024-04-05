package com.rashlight.carnival.communication.grpc;

import com.rashlight.carnival.communication.grpc.autogen.CrashMulServiceGrpc;
import com.rashlight.carnival.communication.grpc.autogen.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class CrashMulServiceClient implements CrashMulInterface {
    private final CrashMulServiceGrpc.CrashMulServiceBlockingStub crashMulServiceStub;

    public CrashMulServiceClient(CrashMulServiceGrpc.CrashMulServiceBlockingStub blockingStub) {
        crashMulServiceStub = blockingStub;
    }

    @Override
    public double getCurrentMultiplier() {
        GetCurrentMultiplierRequest request = GetCurrentMultiplierRequest.newBuilder().build();
        return crashMulServiceStub.getCurrentMultiplier(request).getMultiplier();
    }

    @Override
    public void initiate() {
        InitiateRequest request = InitiateRequest.newBuilder().build();
        Object throwaway = crashMulServiceStub.initiate(request);
    }

    @Override
    public boolean bump() {
        BumpRequest request = BumpRequest.newBuilder().build();
        return crashMulServiceStub.bump(request).getIsFail();
    }

    @Override
    public int getCurrentBumpTime() {
        GetCurrentBumpTimeRequest request = GetCurrentBumpTimeRequest.newBuilder().build();
        return crashMulServiceStub.getCurrentBumpTime(request).getBumpTime();
    }
}
