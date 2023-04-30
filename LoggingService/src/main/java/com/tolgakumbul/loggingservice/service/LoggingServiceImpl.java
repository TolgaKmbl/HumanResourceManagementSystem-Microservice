package com.tolgakumbul.loggingservice.service;

import com.tolgakumbul.loggingservice.dao.LoggingErrorObjectRepository;
import com.tolgakumbul.loggingservice.dao.LoggingObjectRepository;
import com.tolgakumbul.loggingservice.dao.model.LoggingErrorObject;
import com.tolgakumbul.loggingservice.dao.model.LoggingObjectEntity;
import com.tolgakumbul.loggingservice.mapper.LoggingObjectMapper;
import com.tolgakumbul.loggingservice.model.LoggingObjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoggingServiceImpl {

    private final Logger LOGGER = LoggerFactory.getLogger(LoggingServiceImpl.class);

    private final LoggingObjectMapper MAPPER = LoggingObjectMapper.INSTANCE;

    private final LoggingObjectRepository repository;
    private final LoggingErrorObjectRepository errorRepository;

    public LoggingServiceImpl(LoggingObjectRepository repository, LoggingErrorObjectRepository errorRepository) {
        this.repository = repository;
        this.errorRepository = errorRepository;
    }

    @KafkaListener(topics = "${kafka.topic.userservicegeneral.name}", groupId = "group-id")
    public void consumeUsersServiceData(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY)String key, @Payload LoggingObjectDTO value) {
        try{
            LOGGER.info("Received data {}", value);
            LoggingObjectEntity loggingObject = MAPPER.toEntity(value, key);
            repository.insert(loggingObject);
        } catch (Exception e) {
            LOGGER.error("Error receiving data {}: {}", value, e.getMessage());
            LoggingErrorObject errorObject = new LoggingErrorObject();
            errorObject.setErrorMessage(e.getMessage());
            errorObject.setErrorDateTime(LocalDateTime.now());
            errorRepository.insert(errorObject);
        }
    }

}
