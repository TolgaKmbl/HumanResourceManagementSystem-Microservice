package com.tolgakumbul.userservice.mapper;

import com.tolgakumbul.proto.CompanyStaffProto.CompanyStaffData;
import com.tolgakumbul.userservice.entity.CompanyStaff;
import com.tolgakumbul.userservice.model.CompanyStaffDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring")
public interface CompanyStaffMapper {

    CompanyStaffMapper INSTANCE = Mappers.getMapper(CompanyStaffMapper.class);

    CompanyStaffData toGetCompanyStaffResponse(CompanyStaffDTO companyStaffDTO);

    CompanyStaffDTO toCompanyStaffDTO(CompanyStaff companyStaff);

    CompanyStaffDTO toCompanyStaffDTO(CompanyStaffData companyStaff);

}
