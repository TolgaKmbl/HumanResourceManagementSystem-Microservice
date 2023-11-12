package com.tolgakumbul.emailservice.mapper;

import com.tolgakumbul.emailservice.entity.EmailActivationEntity;
import com.tolgakumbul.emailservice.model.EmailDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public interface EmailMapper {

    EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);

    @Mapping(target = "isActivated", constant = "N")
    @Mapping(target = "activationDate", source = "dateTime")
    EmailActivationEntity toEmailActivationEntity(EmailDTO emailDTO, String activationCode, LocalDateTime dateTime);

}
