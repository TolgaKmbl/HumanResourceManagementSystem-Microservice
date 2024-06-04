package com.tolgakumbul.userservice.config;

import com.tolgakumbul.customcontext.CustomSecurityContextService;
import com.tolgakumbul.customcontext.CustomSecurityContextServiceImpl;
import com.tolgakumbul.grpcinterceptor.GrpcClientInterceptor;
import com.tolgakumbul.grpcinterceptor.GrpcServerInterceptor;
import com.tolgakumbul.securitycontexthelper.SecurityContextHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CustomSecurityContextService customSecurityContextService() {
        return new CustomSecurityContextServiceImpl();
    }

    @Bean
    public SecurityContextHelper securityContextHelper() {
        return new SecurityContextHelper();
    }

    @Bean
    public GrpcClientInterceptor grpcClientInterceptor() {
        return new GrpcClientInterceptor();
    }

    @Bean
    public GrpcServerInterceptor grpcServerInterceptor(CustomSecurityContextService contextService) {
        return new GrpcServerInterceptor(contextService);
    }

}
