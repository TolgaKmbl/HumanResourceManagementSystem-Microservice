package com.tolgakumbul.userservice.mapper;

import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.JobSeekersEntity;
import com.tolgakumbul.userservice.model.jobseekers.GetAllJobSeekersRequestDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public interface JobSeekerMapper {

    JobSeekerMapper INSTANCE = Mappers.getMapper(JobSeekerMapper.class);

    ListRequest toListRequest(GetAllJobSeekersRequestDTO requestDTO);

    JobSeekerDTO toJobSeekerDTO(JobSeekersEntity jobSeekersEntity);

    JobSeekersEntity toJobSeekersEntity(JobSeekerDTO jobSeekerDTO);


}
