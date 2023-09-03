package com.tolgakumbul.emailservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JPAConfiguration {
    @Bean
    public CustomAuditorAware auditorProvider(){
        return new CustomAuditorAware();
    }
}
