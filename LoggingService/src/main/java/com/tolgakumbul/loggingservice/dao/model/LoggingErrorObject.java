package com.tolgakumbul.loggingservice.dao.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "loggingerrorcollection")
public class LoggingErrorObject {

    @Id
    private String id;
    private String errorMessage;
    private LocalDateTime errorDateTime;
}
