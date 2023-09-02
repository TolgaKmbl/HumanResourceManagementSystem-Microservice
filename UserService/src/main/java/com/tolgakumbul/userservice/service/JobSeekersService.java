package com.tolgakumbul.userservice.service;

import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.jobseekers.GetAllJobSeekersRequestDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerGeneralResponseDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerListResponseDTO;

public interface JobSeekersService {

    JobSeekerListResponseDTO getAllJobSeekers(GetAllJobSeekersRequestDTO getAllJobSeekersRequestDTO);

    JobSeekerGeneralResponseDTO getJobSeekerById(Long jobSeekerId);

    JobSeekerGeneralResponseDTO getJobSeekerByNationalId(String nationalId);

    JobSeekerGeneralResponseDTO getJobSeekerByName(String firstName, String lastName);

    JobSeekerGeneralResponseDTO updateJobSeeker(JobSeekerDTO jobSeekerDTO);

    JobSeekerGeneralResponseDTO insertJobSeeker(JobSeekerDTO jobSeekerDTO);

    JobSeekerGeneralResponseDTO approveJobSeeker(JobSeekerDTO jobSeekerDTO);

    CommonResponseDTO deleteJobSeeker(Long jobSeekerId);

}
