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


    public void sendKafkaTopic(Object request, Object response, String operationName, String topicName, String entityNameForKafka) {
        KafkaProducerModel kafkaProducerModel = generateKafkaProducerModel(topicName,
                entityNameForKafka + "-" + operationName,
                request,
                response,
                operationName,
                entityNameForKafka,
                "");
        send(kafkaProducerModel);
    }

    public void sendKafkaTopicForError(Object request, String operationName, String errorMessage, String topicName, String entityNameForKafka) {
        KafkaProducerModel kafkaProducerModel = generateKafkaProducerModel(topicName,
                entityNameForKafka + "-" + operationName,
                request,
                null,
                operationName,
                entityNameForKafka,
                errorMessage);
        send(kafkaProducerModel);
    }

    private KafkaProducerModel generateKafkaProducerModel(String topicName,
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

    private boolean send(KafkaProducerModel kafkaProducerModel) {
        try {
            //TODO: Async
            if (isKafkaEnabledForLogging) {
                kafkaTemplate.send(kafkaProducerModel.getTopicName(), kafkaProducerModel.getKeyName(), kafkaProducerModel.getKafkaObject());
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("ERROR while sending {} to topic {} : {}", kafkaProducerModel.getKafkaObject(), kafkaProducerModel.getTopicName(), e.getMessage());
        }
        return false;
    }
}
