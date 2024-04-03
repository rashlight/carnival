package com.rashlight.carnival.communication.grpc;

import com.rashlight.carnival.communication.grpc.autogen.*;
import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@GrpcService
public class CrashMulServiceServer extends CrashMulServiceGrpc.CrashMulServiceImplBase {

    // We did not use Spring Bean to segregate the implementation
    private double multiplier = 0d;
    private double failChance = 0d;
    private int bumpTime = 0;
    private boolean failed = false;

    @Override
    public void getCurrentMultiplier(GetCurrentMultiplierRequest request,
                                     StreamObserver<GetCurrentMultiplierResponse> responseObserver) {
        GetCurrentMultiplierResponse response = GetCurrentMultiplierResponse.newBuilder()
                .setMultiplier(getCurrentMultiplier())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void initiate(InitiateRequest request,
                         StreamObserver<InitiateResponse> responseObserver) {
        initiate();
        InitiateResponse response = InitiateResponse.newBuilder().build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void bump(BumpRequest request,
                     StreamObserver<BumpResponse> responseObserver) {

        BumpResponse response = BumpResponse.newBuilder()
                .setIsFail(bump())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /* Logic area goes here */

    public double getCurrentMultiplier() {
        return multiplier;
    }

    public void initiate() {
        multiplier = 0d;
        failChance = 0d;
        bumpTime = 0;
    }

    public boolean bump() {
        if (!failed) {
            bumpTime = bumpTime + 1;
            BigDecimal multiplierRaw = BigDecimal.valueOf(multiplier + 0.1d * bumpTime);
            multiplier = multiplierRaw.doubleValue();
            BigDecimal failChanceRaw = BigDecimal.valueOf(Math.clamp(0.01d + 0.001d * bumpTime, 0d, 98d)).setScale(3, RoundingMode.HALF_EVEN);
            failChance = failChanceRaw.doubleValue();

            failed = ThreadLocalRandom.current().nextDouble(0, 1) <= failChance;
        }
        return failed;
    }
}
