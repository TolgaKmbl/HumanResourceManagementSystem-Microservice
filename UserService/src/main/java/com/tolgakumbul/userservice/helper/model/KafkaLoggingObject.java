package com.tolgakumbul.userservice.helper.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class KafkaLoggingObject implements Serializable {

    private static final long serialVersionUID = -9220865252564699925L;

    private String operationName;
    private String entityName;
    private Object request;
    private Object response;
    private LocalDateTime instanceId;

}
