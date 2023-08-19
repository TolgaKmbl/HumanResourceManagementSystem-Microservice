package com.tolgakumbul.userservice.dao;

import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.JobSeekersEntity;

import java.util.List;

public interface JobSeekersDao {

    List<JobSeekersEntity> getAllJobSeekers(ListRequest listRequest);

    JobSeekersEntity getJobSeekerById(Long jobSeekerId);

    JobSeekersEntity getJobSeekerByNationalId(String nationalId);

    JobSeekersEntity getJobSeekerByName(String firstName, String lastName);

    Integer updateJobSeeker(JobSeekersEntity jobSeekersEntity);

    Integer insertJobSeeker(JobSeekersEntity jobSeekersEntity);

    Integer approveJobSeeker(JobSeekersEntity jobSeekersEntity);

    Integer deleteJobSeeker(Long jobSeekerId);

}
