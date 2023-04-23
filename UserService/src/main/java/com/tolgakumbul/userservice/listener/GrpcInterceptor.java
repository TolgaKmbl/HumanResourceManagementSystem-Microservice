package com.tolgakumbul.userservice.listener;

import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

@GrpcGlobalServerInterceptor
public class GrpcInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        logRequest(call.getMethodDescriptor().getFullMethodName(), headers);
        ServerCall<ReqT, RespT> wrappedCall = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
            @Override
            public void sendMessage(RespT message) {
                logResponse(message);
                super.sendMessage(message);
            }
        };
        return next.startCall(wrappedCall, headers);
    }

    private void logRequest(String method, Metadata headers) {
        System.out.println("Received request for method " + method + " with headers " + headers);
    }

    private <RespT> void logResponse(RespT response) {
        System.out.println("Sent response " + response);
    }
}
