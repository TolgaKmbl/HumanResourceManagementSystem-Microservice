package com.tolgakumbul.userservice.listener;

import com.tolgakumbul.userservice.helper.JwtValidator;
import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

@GrpcGlobalServerInterceptor
public class GrpcInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        /*TODO: Take the username from the metadata headers and match it with the one in the token*/
        String authToken = headers.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            throw new StatusRuntimeException(Status.UNAUTHENTICATED.withDescription("Invalid authorization header"));
        }
        String token = authToken.substring(7);
        if (!JwtValidator.isValid(token)) {
            throw new StatusRuntimeException(Status.UNAUTHENTICATED.withDescription("Invalid or expired token"));
        }
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
