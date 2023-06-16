package com.tolgakumbul.userservice.config;

import com.tolgakumbul.customcontext.CustomSecurityContextService;
import com.tolgakumbul.customcontext.CustomSecurityContextServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomSecurityContextConfig {

    @Bean
    public CustomSecurityContextService contextService() {
        return new CustomSecurityContextServiceImpl();
    }
}
