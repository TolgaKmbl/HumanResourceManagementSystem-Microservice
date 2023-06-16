package com.tolgakumbul.userservice.helper;

import com.tolgakumbul.userservice.helper.model.kafka.KafkaLoggingObject;
import com.tolgakumbul.userservice.helper.model.kafka.KafkaProducerModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class KafkaProducerHelper {

    private static final Logger LOGGER = LogManager.getLogger(KafkaProducerHelper.class);

    @Value("${kafka.enabled}")
    private Boolean isKafkaEnabledForLogging;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerHelper(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public KafkaProducerModel generateKafkaProducerModel(String topicName,
                                                         String keyName,
                                                         Object request,
                                                         Object response,
                                                         String operationName,
                                                         String entityName,
                                                         String errorMessage) {
        KafkaLoggingObject kafkaLoggingObject = KafkaLoggingObject.builder()
                .request(request)
                .response(response)
                .entityName(entityName)
                .operationName(operationName)
                .instanceId(LocalDateTime.now())
                .errorMessage(errorMessage)
                .build();
        return KafkaProducerModel.builder()
                .topicName(topicName)
                .keyName(keyName)
                .kafkaObject(kafkaLoggingObject)
                .build();
    }

    public boolean send(KafkaProducerModel kafkaProducerModel) {
        try {
            if(isKafkaEnabledForLogging){
                kafkaTemplate.send(kafkaProducerModel.getTopicName(), kafkaProducerModel.getKeyName(), kafkaProducerModel.getKafkaObject());
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("ERROR while sending {} to topic {} : {}", kafkaProducerModel.getKafkaObject(), kafkaProducerModel.getTopicName(), e.getMessage());
        }
        return false;
    }
}
