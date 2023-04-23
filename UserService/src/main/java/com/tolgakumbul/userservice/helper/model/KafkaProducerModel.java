package com.tolgakumbul.userservice.helper.model;

import lombok.Data;

@Data
public class KafkaProducerModel<T> {

    private String topicName;
    private String keyName;
    private T kafkaObject;

}
