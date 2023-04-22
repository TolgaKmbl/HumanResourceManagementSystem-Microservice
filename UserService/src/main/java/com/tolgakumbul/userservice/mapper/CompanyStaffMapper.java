package com.tolgakumbul.userservice.mapper;

import com.google.protobuf.Timestamp;
import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.CompanyStaffProto.CompanyStaffData;
import com.tolgakumbul.proto.CompanyStaffProto.CompanyStaffGeneralResponse;
import com.tolgakumbul.proto.CompanyStaffProto.IsApproved;
import com.tolgakumbul.userservice.entity.CompanyStaffEntity;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffGeneralResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.IsApprovedEnum;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        componentModel = "spring",
        imports = {LocalDateTime.class})
public interface CompanyStaffMapper {

    CompanyStaffMapper INSTANCE = Mappers.getMapper(CompanyStaffMapper.class);

    CompanyStaffData toGetCompanyStaffResponse(CompanyStaffDTO companyStaffDTO);

    @Mapping(target = "isApproved", expression = "java(IsApprovedEnum.fromTextType(companyStaffEntity.getIsApproved()))")
    @Mapping(target = "userId", expression = "java(companyStaffEntity.getUserId() == null ? 0 : companyStaffEntity.getUserId())")
    @Mapping(target = "firstName", source = "firstName", defaultValue = " ")
    @Mapping(target = "lastName", source = "lastName", defaultValue = " ")
    @Mapping(target = "approvalDate", source = "approvalDate", defaultExpression = "java(LocalDateTime.of(1900,1,1,0,0,0,0))")
    CompanyStaffDTO toCompanyStaffDTO(CompanyStaffEntity companyStaffEntity);

    CompanyStaffDTO toCompanyStaffDTO(CompanyStaffData companyStaff);

    @Mapping(target = "isApproved", expression = "java(companyStaffDTO.getIsApproved().getTextType())")
    CompanyStaffEntity toCompanyStaff(CompanyStaffDTO companyStaffDTO);

    CommonResponse toCommonResponse(CommonResponseDTO commonResponseDTO);

    CompanyStaffGeneralResponse toCompanyStaffGeneralResponse(CompanyStaffGeneralResponseDTO companyStaffGeneralResponseDTO);

    @ValueMapping(source = "ACTIVE", target = "ACTIVE")
    @ValueMapping(source = "PASSIVE", target = "PASSIVE")
    @ValueMapping(source = "UNRECOGNIZED", target = "PASSIVE")
    IsApprovedEnum toIsApprovedEnum(IsApproved isApproved);

    @ValueMapping(source = "ACTIVE", target = "ACTIVE")
    @ValueMapping(source = "PASSIVE", target = "PASSIVE")
    IsApproved toIsApproved(IsApprovedEnum isApprovedEnum);

    default Timestamp localDateTimeToTimestamp(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    default LocalDateTime timestampToLocalDateTimeTo(Timestamp timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos(), ZoneOffset.UTC);
    }

}
