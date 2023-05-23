package com.tolgakumbul.userservice.dao;

import com.tolgakumbul.userservice.entity.JobSeekersEntity;

import java.util.List;

public interface JobSeekersDao {

    List<JobSeekersEntity> getAllJobSeekers();

    JobSeekersEntity getJobSeekerById(Long jobSeekerId);

    JobSeekersEntity getJobSeekerByNationalId(String nationalId);

    JobSeekersEntity getJobSeekerByName(String firstName, String lastName);

    Integer updateJobSeeker(JobSeekersEntity jobSeekersEntity);

    Integer insertJobSeeker(JobSeekersEntity jobSeekersEntity);

    Integer approveJobSeeker(Long jobSeekerId);

    Integer deleteJobSeeker(Long jobSeekerId);

}
