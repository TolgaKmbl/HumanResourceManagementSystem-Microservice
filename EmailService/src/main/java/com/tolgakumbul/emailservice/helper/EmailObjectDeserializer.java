package com.tolgakumbul.emailservice.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tolgakumbul.emailservice.model.EmailConfirmationDTO;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EmailObjectDeserializer implements Deserializer<EmailConfirmationDTO> {

    private final Logger LOGGER = LoggerFactory.getLogger(EmailObjectDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public EmailConfirmationDTO deserialize(String topic, byte[] data) {
        LOGGER.info("Serialized data {}", data);
        /*ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            return ois.readObject();
        } */
        try{
            String json = new String(data, StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            EmailConfirmationDTO result = objectMapper.readValue(json, EmailConfirmationDTO.class);
            return result;
        }
        catch (Exception e) {
            LOGGER.error("Error when deserializing byte[] to Object, {0}", e);
            throw new SerializationException("Error when deserializing byte[] to Object");
        }
    }

    @Override
    public void close() {
    }
}




