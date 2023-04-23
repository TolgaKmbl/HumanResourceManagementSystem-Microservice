package com.tolgakumbul.userservice.helper;

import com.tolgakumbul.userservice.helper.model.KafkaProducerModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerHelper<T> {

    private static final Logger LOGGER = LogManager.getLogger(KafkaProducerHelper.class);

    private final KafkaTemplate<String, T> kafkaTemplate;

    public KafkaProducerHelper(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean send(KafkaProducerModel<T> kafkaProducerModel) {
        try {
            kafkaTemplate.send(kafkaProducerModel.getTopicName(), kafkaProducerModel.getKeyName(), kafkaProducerModel.getKafkaObject());
            return true;
        } catch (Exception e) {
            LOGGER.error("ERROR while sending {} to topic {} : {}", kafkaProducerModel.getKafkaObject(), kafkaProducerModel.getTopicName(), e.getMessage());
        }
        return false;
    }
}
