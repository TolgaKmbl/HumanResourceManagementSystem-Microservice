package com.tolgakumbul.userservice.listener;

import io.grpc.*;

public class GrpcClientInterceptor implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        //TODO: update token metadata
        return null;
    }

}
