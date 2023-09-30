package com.tolgakumbul.emailservice.event;

import com.tolgakumbul.emailservice.helper.JsonConverterHelper;
import com.tolgakumbul.emailservice.model.EmailDTO;
import com.tolgakumbul.emailservice.model.confirmation.EmailConfirmationRequestDTO;
import com.tolgakumbul.emailservice.model.confirmation.EmailConfirmationResponseDTO;
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
            EmailDTO emailDTO = JsonConverterHelper.convertToPojo(value, EmailDTO.class);
            emailService.sendEmail(emailDTO);
        } catch (Exception e) {
            LOGGER.error("Error receiving data on EmailServiceListener {}: {}", value, e.getMessage());
        }
    }

    /*@KafkaListener(topics = "${kafka.topic.emailservice.confirming.name}")
    @SendTo("${kafka.topic.emailservice.confirmingreply.name}")*/
    @KafkaListener(topics = "${kafka.topic.consumer}")
    @SendTo("${kafka.topic.producer}")
    public String confirmMail(ConsumerRecord<String, String> value) {
        try {
            LOGGER.info("EmailServiceListener received data for confirmMail {}", value);
            EmailConfirmationRequestDTO emailConfirmationRequestDTO = JsonConverterHelper.convertToPojo(value.value(), EmailConfirmationRequestDTO.class);
            EmailConfirmationResponseDTO emailConfirmationResponseDTO = emailService.confirmEmail(emailConfirmationRequestDTO);
            return JsonConverterHelper.convertToJson(emailConfirmationResponseDTO);
        } catch (Exception e) {
            LOGGER.error("Error receiving data on EmailServiceListener {}: {}", value, e.getMessage());
            throw e;
        }
    }
}
