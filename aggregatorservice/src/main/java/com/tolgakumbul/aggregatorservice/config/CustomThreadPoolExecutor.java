package com.tolgakumbul.aggregatorservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import java.util.concurrent.*;


public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

    private SecurityContextHolderStrategy securityContextHolderStrategy;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomThreadPoolExecutor.class);


    public CustomThreadPoolExecutor(int corePoolSize,
                                    int maximumPoolSize,
                                    Integer keepAliveTime,
                                    Integer queueSize,
                                    ThreadFactory threadFactory,
                                    RejectedExecutionHandler rejectedExecutionHandler,
                                    Long maximumWaitTime,
                                    SecurityContextHolderStrategy securityContextHolderStrategy
    ) {
        super(corePoolSize, maximumPoolSize, (long)keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize),threadFactory,rejectedExecutionHandler);

        this.securityContextHolderStrategy=securityContextHolderStrategy;
    }


    @Override
    public void execute(Runnable runnable) {

        if(runnable==null){
            LOGGER.error("DelegatedThreadPoolExecutor null runnable");
            throw new NullPointerException();
        }else {

            SecurityContext context=this.securityContextHolderStrategy.getContext();

            CustomRunnable customRunnable=
                    new CustomRunnable( context ,runnable);

            super.execute(customRunnable);
        }

    }


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        ((CustomRunnable) r).delegateSecurityContext();
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        this.securityContextHolderStrategy.clearContext();
        super.afterExecute(r, t);
    }

    class CustomRunnable implements Runnable{

        private final SecurityContext sharedContext;
        private final Runnable delegate;

        CustomRunnable(SecurityContext securityContext,Runnable delegate) {
            this.sharedContext = securityContext;
            this.delegate=delegate;
        }


        @Override
        public void run() {
            this.delegate.run();

        }

        void delegateSecurityContext(){
            CustomThreadPoolExecutor.this.securityContextHolderStrategy.setContext(this.sharedContext);
        }

    }
}