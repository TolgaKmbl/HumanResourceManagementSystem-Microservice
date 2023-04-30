package com.tolgakumbul.userservice.mapper;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.EmployersProto.Employer;
import com.tolgakumbul.proto.EmployersProto.EmployerGeneralResponse;
import com.tolgakumbul.userservice.entity.EmployersEntity;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersDTO;
import com.tolgakumbul.userservice.model.employers.EmployersGeneralResponseDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring")
public interface EmployersMapper {

    EmployersMapper INSTANCE = Mappers.getMapper(EmployersMapper.class);

    EmployersDTO toEmployersDTO(EmployersEntity employersEntity);

    EmployersEntity toEmployersEntity(EmployersDTO employersDTO);

    Employer toEmployer(EmployersDTO employersDTO);

    CommonResponse toCommonResponse(CommonResponseDTO commonResponseDTO);

    EmployersDTO toEmployersDTO(Employer employer);

    EmployerGeneralResponse toEmployerGeneralResponse(EmployersGeneralResponseDTO employersGeneralResponseDTO);

}
