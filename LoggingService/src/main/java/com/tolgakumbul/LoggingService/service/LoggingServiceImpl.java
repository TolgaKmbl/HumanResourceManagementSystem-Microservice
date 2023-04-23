package com.tolgakumbul.LoggingService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LoggingServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(LoggingServiceImpl.class);

    @KafkaListener(topics="custom-notification", groupId="group-id")
    public void consumeCustom(Object message) {
        logger.info("Received data {}", message);
    }

}
