package com.rashlight.carnival;

import com.rashlight.carnival.communication.grpc.CrashMulServiceClient;
import com.rashlight.carnival.communication.grpc.autogen.CrashMulServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClientBean;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@GrpcClientBean(
        clazz = CrashMulServiceGrpc.CrashMulServiceBlockingStub.class,
        beanName = "crashMulServiceBlockingStub",
        client = @GrpcClient("CrashMulService")
)
public class GrpcConfiguration {
    @Bean
    CrashMulServiceClient crashMulServiceClient(@Autowired CrashMulServiceGrpc.CrashMulServiceBlockingStub blockingStub) {
        return new CrashMulServiceClient(blockingStub);
    }

    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        return new BasicGrpcAuthenticationReader();
    }
}
