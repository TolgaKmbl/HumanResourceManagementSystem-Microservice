package com.tolgakumbul.userservice.helper.aspect;

import com.tolgakumbul.userservice.exception.ExceptionMessageUtil;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.exception.model.GeneralErrorResponse;
import com.tolgakumbul.userservice.helper.KafkaProducerHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class KafkaHelperAspect {

    private static final Logger LOGGER = LogManager.getLogger(KafkaHelperAspect.class);

    private final KafkaProducerHelper kafkaProducerHelper;

    public KafkaHelperAspect(KafkaProducerHelper kafkaProducerHelper) {
        this.kafkaProducerHelper = kafkaProducerHelper;
    }

    @AfterReturning(value = "@annotation(kafkaHelper)",
            argNames = "joinPoint, kafkaHelper, response",
            returning = "response")
    public void afterMethodExecution(JoinPoint joinPoint, KafkaHelper kafkaHelper, Object response) {

        Object[] args = joinPoint.getArgs();
        Object request = ObjectUtils.isEmpty(args) ? args : args[0];
        LOGGER.info("Sending request - {}, response - {} to kafka in KafkaHelper.afterMethodExecution", request, response);
        kafkaProducerHelper.sendKafkaTopic(request,
                response,
                kafkaHelper.operationName(),
                kafkaHelper.topicName(),
                kafkaHelper.entityNameForKafka());

    }

    @AfterThrowing(value = "@annotation(kafkaHelper)",
            argNames = "joinPoint, kafkaHelper, exception",
            throwing = "exception")
    public void afterMethodException(JoinPoint joinPoint, KafkaHelper kafkaHelper, UsersException exception) {

        Object[] args = joinPoint.getArgs();
        Object request = ObjectUtils.isEmpty(args) ? args : args[0];
        GeneralErrorResponse generalErrorResponse = ExceptionMessageUtil.handleErrorResponse(exception);
        LOGGER.info("Sending request - {}, exception - {} to kafka in KafkaHelper.afterMethodException", request, generalErrorResponse.getErrorMessage());
        kafkaProducerHelper.sendKafkaTopic(request,
                kafkaHelper.operationName(),
                generalErrorResponse.getErrorMessage(),
                kafkaHelper.topicName(),
                kafkaHelper.entityNameForKafka());

    }

}
