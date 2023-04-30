package com.tolgakumbul.loggingservice.dao.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "loggingcollection")
@Data
public class LoggingObjectEntity {

    @Id
    private String id;
    private String keyName;
    private String operationName;
    private String entityName;
    private Object request;
    private Object response;
    private LocalDateTime instanceId;
    private String errorMessage;
}
