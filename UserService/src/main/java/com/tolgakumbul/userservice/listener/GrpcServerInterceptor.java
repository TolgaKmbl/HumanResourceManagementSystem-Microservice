package com.tolgakumbul.userservice.listener;

import com.tolgakumbul.userservice.helper.SecurityContextHelper;
import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@GrpcGlobalServerInterceptor
public class GrpcServerInterceptor implements ServerInterceptor {

    private static final Logger LOGGER = LogManager.getLogger(GrpcServerInterceptor.class);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        SecurityContextHelper.resetSecurityContext();
        /*TODO: Take the username from the metadata headers and match it with the one in the token*/
        String authToken = headers.get(Metadata.Key.of(SecurityContextHelper.AUTHORIZATION_HEADER, Metadata.ASCII_STRING_MARSHALLER));
        if (authToken == null || !authToken.startsWith(SecurityContextHelper.BEARER)) {
            throw new StatusRuntimeException(Status.UNAUTHENTICATED.withDescription("Invalid authorization header"));
        }
        String token = authToken.substring(7);
        if (!SecurityContextHelper.isJwtTokenValid(token)) {
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
        LOGGER.info("Received request for method {} with headers {}", method, headers);
    }

    private <RespT> void logResponse(RespT response) {
        LOGGER.info("Sent response {}", response);
    }
}
