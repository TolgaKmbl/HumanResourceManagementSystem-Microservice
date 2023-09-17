package com.tolgakumbul.emailservice.listener;

import com.tolgakumbul.emailservice.model.EmailConfirmationDTO;
import com.tolgakumbul.emailservice.model.EmailDTO;
import com.tolgakumbul.emailservice.service.EmailService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceListener {

    private final Logger LOGGER = LoggerFactory.getLogger(EmailServiceListener.class);

    private final EmailService emailService;

    public EmailServiceListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${kafka.topic.emailservice.sending.name}", groupId = "group-id")
    public void sendEmail(EmailDTO value) {
        try {
            LOGGER.info("EmailServiceListener received data for sendEmail {}", value);
        } catch (Exception e) {
            LOGGER.error("Error receiving data on EmailServiceListener {}: {}", value, e.getMessage());
        }
    }

    @KafkaListener(topics = "${kafka.topic.emailservice.resending.name}", groupId = "group-id")
    public void resendEmail(EmailDTO value) {
        try {
            LOGGER.info("EmailServiceListener received data for resendEmail {}", value);
        } catch (Exception e) {
            LOGGER.error("Error receiving data on EmailServiceListener {}: {}", value, e.getMessage());
        }
    }

    @KafkaListener(topics = "${kafka.topic.consumer}")
    @SendTo("${kafka.topic.producer}")
    public EmailConfirmationDTO confirmMail(ConsumerRecord<String, EmailConfirmationDTO> value, @Header(KafkaHeaders.CORRELATION_ID) byte[] correlation) {
        try {
            LOGGER.info("EmailServiceListener received data for confirmMail {}", value);
            value.headers().add(KafkaHeaders.CORRELATION_ID, correlation);
            value.value().setStatus("Active");
            return value.value();
        } catch (Exception e) {
            LOGGER.error("Error receiving data on EmailServiceListener {}: {}", value, e.getMessage());
            throw e;
        }
    }
}
