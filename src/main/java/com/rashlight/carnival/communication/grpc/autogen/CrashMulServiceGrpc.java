package com.rashlight.carnival.communication.grpc.autogen;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.58.0)",
    comments = "Source: guess-num.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CrashMulServiceGrpc {

  private CrashMulServiceGrpc() {}

  public static final String SERVICE_NAME = "CrashMulService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<GetCurrentMultiplierRequest,
      GetCurrentMultiplierResponse> getGetCurrentMultiplierMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getCurrentMultiplier",
      requestType = GetCurrentMultiplierRequest.class,
      responseType = GetCurrentMultiplierResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GetCurrentMultiplierRequest,
      GetCurrentMultiplierResponse> getGetCurrentMultiplierMethod() {
    io.grpc.MethodDescriptor<GetCurrentMultiplierRequest, GetCurrentMultiplierResponse> getGetCurrentMultiplierMethod;
    if ((getGetCurrentMultiplierMethod = CrashMulServiceGrpc.getGetCurrentMultiplierMethod) == null) {
      synchronized (CrashMulServiceGrpc.class) {
        if ((getGetCurrentMultiplierMethod = CrashMulServiceGrpc.getGetCurrentMultiplierMethod) == null) {
          CrashMulServiceGrpc.getGetCurrentMultiplierMethod = getGetCurrentMultiplierMethod =
              io.grpc.MethodDescriptor.<GetCurrentMultiplierRequest, GetCurrentMultiplierResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getCurrentMultiplier"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GetCurrentMultiplierRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GetCurrentMultiplierResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CrashMulServiceMethodDescriptorSupplier("getCurrentMultiplier"))
              .build();
        }
      }
    }
    return getGetCurrentMultiplierMethod;
  }

  private static volatile io.grpc.MethodDescriptor<InitiateRequest,
      InitiateResponse> getInitiateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "initiate",
      requestType = InitiateRequest.class,
      responseType = InitiateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<InitiateRequest,
      InitiateResponse> getInitiateMethod() {
    io.grpc.MethodDescriptor<InitiateRequest, InitiateResponse> getInitiateMethod;
    if ((getInitiateMethod = CrashMulServiceGrpc.getInitiateMethod) == null) {
      synchronized (CrashMulServiceGrpc.class) {
        if ((getInitiateMethod = CrashMulServiceGrpc.getInitiateMethod) == null) {
          CrashMulServiceGrpc.getInitiateMethod = getInitiateMethod =
              io.grpc.MethodDescriptor.<InitiateRequest, InitiateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "initiate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  InitiateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  InitiateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CrashMulServiceMethodDescriptorSupplier("initiate"))
              .build();
        }
      }
    }
    return getInitiateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<BumpRequest,
      BumpResponse> getBumpMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "bump",
      requestType = BumpRequest.class,
      responseType = BumpResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<BumpRequest,
      BumpResponse> getBumpMethod() {
    io.grpc.MethodDescriptor<BumpRequest, BumpResponse> getBumpMethod;
    if ((getBumpMethod = CrashMulServiceGrpc.getBumpMethod) == null) {
      synchronized (CrashMulServiceGrpc.class) {
        if ((getBumpMethod = CrashMulServiceGrpc.getBumpMethod) == null) {
          CrashMulServiceGrpc.getBumpMethod = getBumpMethod =
              io.grpc.MethodDescriptor.<BumpRequest, BumpResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "bump"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  BumpRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  BumpResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CrashMulServiceMethodDescriptorSupplier("bump"))
              .build();
        }
      }
    }
    return getBumpMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CrashMulServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CrashMulServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CrashMulServiceStub>() {
        @Override
        public CrashMulServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CrashMulServiceStub(channel, callOptions);
        }
      };
    return CrashMulServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CrashMulServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CrashMulServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CrashMulServiceBlockingStub>() {
        @Override
        public CrashMulServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CrashMulServiceBlockingStub(channel, callOptions);
        }
      };
    return CrashMulServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CrashMulServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CrashMulServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CrashMulServiceFutureStub>() {
        @Override
        public CrashMulServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CrashMulServiceFutureStub(channel, callOptions);
        }
      };
    return CrashMulServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getCurrentMultiplier(GetCurrentMultiplierRequest request,
                                      io.grpc.stub.StreamObserver<GetCurrentMultiplierResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCurrentMultiplierMethod(), responseObserver);
    }

    /**
     */
    default void initiate(InitiateRequest request,
                          io.grpc.stub.StreamObserver<InitiateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getInitiateMethod(), responseObserver);
    }

    /**
     */
    default void bump(BumpRequest request,
                      io.grpc.stub.StreamObserver<BumpResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBumpMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CrashMulService.
   */
  public static abstract class CrashMulServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return CrashMulServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CrashMulService.
   */
  public static final class CrashMulServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CrashMulServiceStub> {
    private CrashMulServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected CrashMulServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CrashMulServiceStub(channel, callOptions);
    }

    /**
     */
    public void getCurrentMultiplier(GetCurrentMultiplierRequest request,
                                     io.grpc.stub.StreamObserver<GetCurrentMultiplierResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCurrentMultiplierMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void initiate(InitiateRequest request,
                         io.grpc.stub.StreamObserver<InitiateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getInitiateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void bump(BumpRequest request,
                     io.grpc.stub.StreamObserver<BumpResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBumpMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CrashMulService.
   */
  public static final class CrashMulServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CrashMulServiceBlockingStub> {
    private CrashMulServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected CrashMulServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CrashMulServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public GetCurrentMultiplierResponse getCurrentMultiplier(GetCurrentMultiplierRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCurrentMultiplierMethod(), getCallOptions(), request);
    }

    /**
     */
    public InitiateResponse initiate(InitiateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getInitiateMethod(), getCallOptions(), request);
    }

    /**
     */
    public BumpResponse bump(BumpRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBumpMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CrashMulService.
   */
  public static final class CrashMulServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CrashMulServiceFutureStub> {
    private CrashMulServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected CrashMulServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CrashMulServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<GetCurrentMultiplierResponse> getCurrentMultiplier(
        GetCurrentMultiplierRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCurrentMultiplierMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<InitiateResponse> initiate(
        InitiateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getInitiateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<BumpResponse> bump(
        BumpRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBumpMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CURRENT_MULTIPLIER = 0;
  private static final int METHODID_INITIATE = 1;
  private static final int METHODID_BUMP = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CURRENT_MULTIPLIER:
          serviceImpl.getCurrentMultiplier((GetCurrentMultiplierRequest) request,
              (io.grpc.stub.StreamObserver<GetCurrentMultiplierResponse>) responseObserver);
          break;
        case METHODID_INITIATE:
          serviceImpl.initiate((InitiateRequest) request,
              (io.grpc.stub.StreamObserver<InitiateResponse>) responseObserver);
          break;
        case METHODID_BUMP:
          serviceImpl.bump((BumpRequest) request,
              (io.grpc.stub.StreamObserver<BumpResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetCurrentMultiplierMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              GetCurrentMultiplierRequest,
              GetCurrentMultiplierResponse>(
                service, METHODID_GET_CURRENT_MULTIPLIER)))
        .addMethod(
          getInitiateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              InitiateRequest,
              InitiateResponse>(
                service, METHODID_INITIATE)))
        .addMethod(
          getBumpMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              BumpRequest,
              BumpResponse>(
                service, METHODID_BUMP)))
        .build();
  }

  private static abstract class CrashMulServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CrashMulServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return CrashMulProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CrashMulService");
    }
  }

  private static final class CrashMulServiceFileDescriptorSupplier
      extends CrashMulServiceBaseDescriptorSupplier {
    CrashMulServiceFileDescriptorSupplier() {}
  }

  private static final class CrashMulServiceMethodDescriptorSupplier
      extends CrashMulServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CrashMulServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CrashMulServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CrashMulServiceFileDescriptorSupplier())
              .addMethod(getGetCurrentMultiplierMethod())
              .addMethod(getInitiateMethod())
              .addMethod(getBumpMethod())
              .build();
        }
      }
    }
    return result;
  }
}
