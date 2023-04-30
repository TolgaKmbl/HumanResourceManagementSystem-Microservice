package com.tolgakumbul.loggingservice.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LoggingObjectDTO implements Serializable {

    private static final long serialVersionUID = 2831575560946084094L;

    private String operationName;
    private String entityName;
    private Object request;
    private Object response;
    private LocalDateTime instanceId;
    private String errorMessage;
}
