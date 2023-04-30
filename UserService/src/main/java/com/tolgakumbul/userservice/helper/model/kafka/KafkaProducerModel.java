package com.tolgakumbul.userservice.helper.model.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaProducerModel {

    private String topicName;
    private String keyName;
    private KafkaLoggingObject kafkaObject;

}
