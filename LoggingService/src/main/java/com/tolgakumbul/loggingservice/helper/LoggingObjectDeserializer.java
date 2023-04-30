package com.tolgakumbul.loggingservice.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tolgakumbul.loggingservice.model.LoggingObjectDTO;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class LoggingObjectDeserializer implements Deserializer<LoggingObjectDTO> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public LoggingObjectDTO deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(new String(data, "UTF-8"), LoggingObjectDTO.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to LoggingObject");
        }
    }

    @Override
    public void close() {
    }
}




