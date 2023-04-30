package com.tolgakumbul.userservice.helper.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tolgakumbul.userservice.helper.model.KafkaLoggingObject;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class KafkaLoggingObjectSerializer implements Serializer<KafkaLoggingObject> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, KafkaLoggingObject data) {
        try {
            if (data == null) {
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing...");
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing KafkaLoggingObject to byte[]");
        }
    }

    @Override
    public void close() {
    }
}