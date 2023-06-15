package com.tolgakumbul.authservice.helper;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogExceptionAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogExceptionAspect.class);

    @Before(value = "@annotation(logException)",
            argNames = "joinPoint, logException")
    public void exceptionLogger(JoinPoint joinPoint, LogException logException) {
        Object[] args = joinPoint.getArgs();
        if (!ObjectUtils.isEmpty(args)) {
            Throwable rootCause = ExceptionUtils.getRootCause((Throwable) args[0]);
            String className = "";
            String exceptionMessage = "";
            if (rootCause != null) {
                className = rootCause.getClass().getName();
                exceptionMessage = rootCause.getMessage();
            }
            LOGGER.error("Handled exception in auth service: {} - {}", className, exceptionMessage);
        }
    }

}
