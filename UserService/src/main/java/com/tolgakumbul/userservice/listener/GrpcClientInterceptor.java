package com.tolgakumbul.userservice.listener;

import com.tolgakumbul.customcontext.CustomSecurityContextMapper;
import com.tolgakumbul.customcontext.CustomSecurityContextService;
import io.grpc.*;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcGlobalClientInterceptor
public class GrpcClientInterceptor implements ClientInterceptor {

    @Autowired
    private CustomSecurityContextService contextService;

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        return new
                ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {
                    @Override
                    public void start(Listener<RespT> responseListener, Metadata headers) {
                        CustomSecurityContextMapper.toMetadata(contextService.getContext(), headers);
                        super.start(responseListener, headers);
                    }
                };
    }

}
