package com.tolgakumbul.userservice.mapper;

import com.google.protobuf.Timestamp;
import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.proto.JobSeekersProto.JobSeeker;
import com.tolgakumbul.proto.JobSeekersProto.JobSeekerByIdRequest;
import com.tolgakumbul.proto.JobSeekersProto.JobSeekerGeneralResponse;
import com.tolgakumbul.proto.JobSeekersProto.JobSeekerListGeneralRequest;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.JobSeekersEntity;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.jobseekers.GetAllJobSeekersRequestDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerGeneralResponseDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public interface JobSeekerMapper {

    JobSeekerMapper INSTANCE = Mappers.getMapper(JobSeekerMapper.class);

    ListRequest toListRequest(GetAllJobSeekersRequestDTO requestDTO);

    JobSeekerDTO toJobSeekerDTO(JobSeekersEntity jobSeekersEntity);

    JobSeekersEntity toJobSeekersEntity(JobSeekerDTO jobSeekerDTO);

    GetAllJobSeekersRequestDTO toGetAllJobSeekersRequestDTO(JobSeekerListGeneralRequest generalRequest);

    JobSeeker toJobSeeker(JobSeekerDTO jobSeekerDTO);

    JobSeekerDTO toJobSeekerDTO(JobSeeker jobSeeker);

    JobSeekerGeneralResponse toJobSeekerGeneralResponse(JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO);

    JobSeekerDTO toJobSeekerDTO(JobSeekerByIdRequest jobSeekerByIdRequest);

    CommonResponse toCommonResponse(CommonResponseDTO commonResponseDTO);

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
