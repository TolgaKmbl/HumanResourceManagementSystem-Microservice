package com.tolgakumbul.loggingservice.mapper;

import com.tolgakumbul.loggingservice.dao.model.LoggingObjectEntity;
import com.tolgakumbul.loggingservice.model.LoggingObjectDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring")
public interface LoggingObjectMapper {

    LoggingObjectMapper INSTANCE = Mappers.getMapper(LoggingObjectMapper.class);

    LoggingObjectEntity toEntity(LoggingObjectDTO loggingObjectDTO, String keyName);

}
