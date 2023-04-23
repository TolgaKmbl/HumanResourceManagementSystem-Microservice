package com.tolgakumbul.userservice.helper.model;

import lombok.Data;

@Data
public class KafkaProducerModel {

    private String topicName;
    private String keyName;
    private Object kafkaObject;

}
