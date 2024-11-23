package com.tolgakumbul.aggregatorservice.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@ConfigurationProperties(
        prefix = "aggregator-service.application.threadpool"
)
@Data
public class CustomThreadPoolConfig {

    private String name;
    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private Integer keepAliveTime;
    private Long maxWaitTime;
    private Integer queueSize;
    private Boolean daemonThreads = Boolean.FALSE;
    private SecurityContextHolderStrategy securityContext;

    @Autowired
    public CustomThreadPoolConfig() {

        this.securityContext = SecurityContextHolder.getContextHolderStrategy();
    }

    @Bean(name = "aggregatorThreadPool")
    public CustomThreadPoolExecutor getThreadPoolExecutor() {
        ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat(this.name + "-%d")
                .setDaemon(this.daemonThreads).build();

        RejectedExecutionHandler rejectedExecutionHandler = getRejectedExecutionHandler();

        return new CustomThreadPoolExecutor(this.corePoolSize,
                this.maximumPoolSize,
                this.keepAliveTime,
                this.queueSize,
                threadFactory,
                rejectedExecutionHandler,
                this.maxWaitTime,
                this.securityContext);


    }


    private RejectedExecutionHandler getRejectedExecutionHandler() {

        return new ThreadPoolExecutor.AbortPolicy();

    }

}
