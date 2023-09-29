package com.tolgakumbul.emailservice.event;

import com.tolgakumbul.emailservice.helper.JsonConverterHelper;
import com.tolgakumbul.emailservice.model.EmailConfirmationDTO;
import com.tolgakumbul.emailservice.service.EmailService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
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
    public void sendEmail(String value) {
        try {
            LOGGER.info("EmailServiceListener received data for sendEmail {}", value);
        } catch (Exception e) {
            LOGGER.error("Error receiving data on EmailServiceListener {}: {}", value, e.getMessage());
        }
    }

    @KafkaListener(topics = "${kafka.topic.emailservice.resending.name}", groupId = "group-id")
    public void resendEmail(String value) {
        try {
            LOGGER.info("EmailServiceListener received data for resendEmail {}", value);
        } catch (Exception e) {
            LOGGER.error("Error receiving data on EmailServiceListener {}: {}", value, e.getMessage());
        }
    }

    @KafkaListener(topics = "${kafka.topic.consumer}")
    @SendTo("${kafka.topic.producer}")
    public String confirmMail(ConsumerRecord<String, String> value) {
        try {
            LOGGER.info("EmailServiceListener received data for confirmMail {}", value);
            EmailConfirmationDTO emailConfirmationDTO = JsonConverterHelper.convertToPojo(value.value(), EmailConfirmationDTO.class);
            emailConfirmationDTO.setStatus("ACTIVE");
            return JsonConverterHelper.convertToJson(emailConfirmationDTO);
        } catch (Exception e) {
            LOGGER.error("Error receiving data on EmailServiceListener {}: {}", value, e.getMessage());
            throw e;
        }
    }
}
