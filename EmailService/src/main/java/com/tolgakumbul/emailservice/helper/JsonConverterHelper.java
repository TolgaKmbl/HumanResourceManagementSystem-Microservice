package com.tolgakumbul.emailservice.helper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class JsonConverterHelper {

    private static final Logger LOGGER = LogManager.getLogger(JsonConverterHelper.class);

    private static Map<Class, ObjectMapper> mapperInstances = new HashMap<>();

    public static final String convertToJson(Object obj) {
        if (obj == null) {
            LOGGER.error("JsonConverterHelper error on convertToJson: Object is null");
            throw new RuntimeException("JsonConverterHelper error on convertToJson");
        }

        ObjectMapper mapperInstance = getInstance(obj.getClass());

        try {
            return mapperInstance.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("JsonConverterHelper error on convertToJsonString {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public static final <T> T convertToPojo(String json, Class<T> clazz) {

        ObjectMapper mapperInstance = getInstance(clazz);

        try {
            return mapperInstance.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            LOGGER.error("JsonConverterHelper error on convertToPojo {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public static final <T> T convertToPojo(String json, String clazz) {
        try {
            Class<?> responseClass = Class.forName(clazz);

            ObjectMapper mapperInstance = getInstance(responseClass);


            return (T) mapperInstance.readValue(json, responseClass);
        } catch (JsonProcessingException | ClassNotFoundException e) {
            LOGGER.error("JsonConverterHelper error on convertToPojo {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private static ObjectMapper getInstance(Class clazz) {

        ObjectMapper mapper = mapperInstances.getOrDefault(clazz, null);

        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.registerModule(new JavaTimeModule());
            mapper.registerModule(new Jdk8Module());
            mapperInstances.put(clazz, mapper);
        }

        return mapper;

    }

}
