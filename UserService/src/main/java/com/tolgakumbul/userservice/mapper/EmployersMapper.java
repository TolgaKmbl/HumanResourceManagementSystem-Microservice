package com.tolgakumbul.userservice.mapper;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.EmployersProto;
import com.tolgakumbul.proto.EmployersProto.Employer;
import com.tolgakumbul.proto.EmployersProto.EmployerGeneralResponse;
import com.tolgakumbul.proto.EmployersProto.EmployerListGeneralRequest;
import com.tolgakumbul.userservice.dao.model.EmployersByCompanyNameRequest;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.EmployersEntity;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersByCompanyNameRequestDTO;
import com.tolgakumbul.userservice.model.employers.EmployersDTO;
import com.tolgakumbul.userservice.model.employers.EmployersGeneralResponseDTO;
import com.tolgakumbul.userservice.model.employers.GetAllEmployersRequestDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public interface EmployersMapper {

    EmployersMapper INSTANCE = Mappers.getMapper(EmployersMapper.class);

    GetAllEmployersRequestDTO toGetAllEmployersRequestDTO(EmployerListGeneralRequest grpcRequest);

    EmployersByCompanyNameRequestDTO toEmployersByCompanyNameRequestDTO(EmployersProto.EmployersByCompanyNameRequest grpcRequest);

    EmployersByCompanyNameRequest toEmployersByCompanyNameRequest(EmployersByCompanyNameRequestDTO requestDTO);

    EmployersDTO toEmployersDTO(EmployersEntity employersEntity);

    EmployersEntity toEmployersEntity(EmployersDTO employersDTO);

    Employer toEmployer(EmployersDTO employersDTO);

    CommonResponse toCommonResponse(CommonResponseDTO commonResponseDTO);

    EmployersDTO toEmployersDTO(Employer employer);

    EmployerGeneralResponse toEmployerGeneralResponse(EmployersGeneralResponseDTO employersGeneralResponseDTO);

    ListRequest toListRequest(GetAllEmployersRequestDTO requestDTO);

}
