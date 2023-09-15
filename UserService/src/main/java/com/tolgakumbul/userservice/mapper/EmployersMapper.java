package com.tolgakumbul.userservice.mapper;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.EmployersProto;
import com.tolgakumbul.proto.EmployersProto.Employer;
import com.tolgakumbul.proto.EmployersProto.EmployerGeneralResponse;
import com.tolgakumbul.proto.EmployersProto.EmployerListGeneralRequest;
import com.tolgakumbul.proto.EmployersProto.EmployerListGeneralResponse;
import com.tolgakumbul.userservice.dao.model.EmployersByCompanyNameRequest;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.EmployersEntity;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.employers.*;
import org.mapstruct.*;
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

    @Mapping(source = "employerList", target = "employerListList")
    EmployerListGeneralResponse toEmployerListGeneralResponse(EmployersListResponseDTO listResponseDTO);

    ListRequest toListRequest(GetAllEmployersRequestDTO requestDTO);

}
