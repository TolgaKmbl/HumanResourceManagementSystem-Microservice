package com.tolgakumbul.userservice.listener;

import com.tolgakumbul.customcontext.CustomSecurityContext;
import com.tolgakumbul.customcontext.CustomSecurityContextService;
import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.helper.SecurityContextHelper;
import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@GrpcGlobalServerInterceptor
public class GrpcServerInterceptor implements ServerInterceptor {

    private static final Logger LOGGER = LogManager.getLogger(GrpcServerInterceptor.class);

    private final CustomSecurityContextService contextService;

    public GrpcServerInterceptor(CustomSecurityContextService customSecurityContextHelper) {
        this.contextService = customSecurityContextHelper;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        SecurityContextHelper.resetSecurityContext();
        /*TODO: Take the username from the metadata headers and match it with the one in the token*/
        String authToken = headers.get(Metadata.Key.of(Constants.AUTHORIZATION_HEADER, Metadata.ASCII_STRING_MARSHALLER));
        String guid = headers.get(Metadata.Key.of(Constants.GUID_HEADER, Metadata.ASCII_STRING_MARSHALLER));
        if (authToken == null || !authToken.startsWith(Constants.BEARER)) {
            throw new StatusRuntimeException(Status.UNAUTHENTICATED.withDescription("Invalid authorization header"));
        }
        String token = authToken.substring(7);
        if (!SecurityContextHelper.isJwtTokenValid(token)) {
            throw new StatusRuntimeException(Status.UNAUTHENTICATED.withDescription("Invalid or expired token"));
        }
        this.contextService.setContext(new CustomSecurityContext(token, guid));
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
