package com.tolgakumbul.LoggingService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LoggingServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(LoggingServiceImpl.class);

    @KafkaListener(topics = "${kafka.topic.companystaffgeneralresponse.name}", groupId = "group-id")
    public void consumeCompanyStaffGeneralResponse(Object message) {
        logger.info("Received data {}", message);
    }

}
