package com.tolgakumbul.userservice.service.impl;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.constants.ErrorCode;
import com.tolgakumbul.userservice.dao.JobSeekersDao;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.JobSeekersEntity;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.helper.aspect.KafkaHelper;
import com.tolgakumbul.userservice.mapper.JobSeekerMapper;
import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.IsApprovedEnum;
import com.tolgakumbul.userservice.model.jobseekers.GetAllJobSeekersRequestDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerGeneralResponseDTO;
import com.tolgakumbul.userservice.model.jobseekers.JobSeekerListResponseDTO;
import com.tolgakumbul.userservice.service.JobSeekersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobSeekersServiceImpl implements JobSeekersService {

    private static final Logger LOGGER = LogManager.getLogger(JobSeekersServiceImpl.class);

    private final JobSeekerMapper MAPPER = JobSeekerMapper.INSTANCE;

    private final JobSeekersDao jobSeekersDao;

    public JobSeekersServiceImpl(JobSeekersDao jobSeekersDao) {
        this.jobSeekersDao = jobSeekersDao;
    }

    @Override
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.JOBSEEKER_KAFKA, operationName = Constants.GET_ALL)
    public JobSeekerListResponseDTO getAllJobSeekers(GetAllJobSeekersRequestDTO getAllJobSeekersRequestDTO) {
        try {
            ListRequest listRequest = MAPPER.toListRequest(getAllJobSeekersRequestDTO);
            List<JobSeekersEntity> allJobSeekers = jobSeekersDao.getAllJobSeekers(listRequest);
            List<JobSeekerDTO> jobSeekerDTOList = allJobSeekers.stream().map(MAPPER::toJobSeekerDTO).collect(Collectors.toList());
            JobSeekerListResponseDTO jobSeekerListResponseDTO = new JobSeekerListResponseDTO(jobSeekerDTOList);

            return jobSeekerListResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in JobSeekersServiceImpl.getAllJobSeekers : {}", e.getMessage());
            throw new UsersException(ErrorCode.ALL_JOB_SEEKERS_FETCH_ERROR, e.getMessage());
        }
    }

    @Override
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.JOBSEEKER_KAFKA, operationName = Constants.GET_BY_ID)
    public JobSeekerGeneralResponseDTO getJobSeekerById(Long jobSeekerId) {
        try {
            JobSeekersEntity jobSeekersById = jobSeekersDao.getJobSeekerById(jobSeekerId);
            JobSeekerDTO jobSeekerDTO = MAPPER.toJobSeekerDTO(jobSeekersById);
            JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO(jobSeekerDTO);

            return jobSeekerGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in JobSeekersServiceImpl.getJobSeekerById : {}", e.getMessage());
            throw new UsersException(ErrorCode.JOB_SEEKER_BY_ID_FETCH_ERROR, e.getMessage());
        }
    }

    @Override
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.JOBSEEKER_KAFKA, operationName = Constants.GET_BY_NATIONAL_ID)
    public JobSeekerGeneralResponseDTO getJobSeekerByNationalId(String nationalId) {
        try {
            JobSeekersEntity jobSeekersByNationalId = jobSeekersDao.getJobSeekerByNationalId(nationalId);
            JobSeekerDTO jobSeekerDTO = MAPPER.toJobSeekerDTO(jobSeekersByNationalId);
            JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO(jobSeekerDTO);

            return jobSeekerGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in JobSeekersServiceImpl.getJobSeekerByNationalId : {}", e.getMessage());
            throw new UsersException(ErrorCode.JOB_SEEKER_BY_NATIONAL_ID_FETCH_ERROR, e.getMessage());
        }
    }

    @Override
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.JOBSEEKER_KAFKA, operationName = Constants.GET_BY_NAME)
    public JobSeekerGeneralResponseDTO getJobSeekerByName(String firstName, String lastName) {
        try {
            JobSeekersEntity jobSeekerByName = jobSeekersDao.getJobSeekerByName(firstName, lastName);
            JobSeekerDTO jobSeekerDTO = MAPPER.toJobSeekerDTO(jobSeekerByName);
            JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO(jobSeekerDTO);

            return jobSeekerGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in JobSeekersServiceImpl.getJobSeekerByName : {}", e.getMessage());
            throw new UsersException(ErrorCode.JOB_SEEKER_BY_NAME_FETCH_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.JOBSEEKER_KAFKA, operationName = Constants.UPDATE)
    public JobSeekerGeneralResponseDTO updateJobSeeker(JobSeekerDTO jobSeekerDTO) {
        try {
            jobSeekersDao.updateJobSeeker(MAPPER.toJobSeekersEntity(jobSeekerDTO));

            JobSeekersEntity jobSeekerById = jobSeekersDao.getJobSeekerById(jobSeekerDTO.getUserId());

            JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO(MAPPER.toJobSeekerDTO(jobSeekerById));

            return jobSeekerGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in JobSeekersServiceImpl.updateJobSeeker : {}", e.getMessage());
            throw new UsersException(ErrorCode.JOB_SEEKER_UPDATE_ERROR, jobSeekerDTO.getUserId(), e.getMessage());
        }
    }

    @Override
    @Transactional
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.JOBSEEKER_KAFKA, operationName = Constants.INSERT)
    public JobSeekerGeneralResponseDTO insertJobSeeker(JobSeekerDTO jobSeekerDTO) {
        try {
            Long latestUserId = jobSeekersDao.getLatestUserId();
            jobSeekerDTO.setUserId(++latestUserId);
            jobSeekersDao.insertJobSeeker(MAPPER.toJobSeekersEntity(jobSeekerDTO));

            JobSeekersEntity jobSeekerById = jobSeekersDao.getJobSeekerById(jobSeekerDTO.getUserId());

            JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO(MAPPER.toJobSeekerDTO(jobSeekerById));

            return jobSeekerGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in JobSeekersServiceImpl.insertJobSeeker : {}", e.getMessage());
            throw new UsersException(ErrorCode.JOB_SEEKER_INSERT_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.JOBSEEKER_KAFKA, operationName = Constants.APPROVE)
    public JobSeekerGeneralResponseDTO approveJobSeeker(JobSeekerDTO jobSeekerDTO) {
        try {
            JobSeekersEntity jobSeekerById = jobSeekersDao.getJobSeekerById(jobSeekerDTO.getUserId());
            jobSeekerById.setIsApproved(IsApprovedEnum.ACTIVE.getTextType());
            jobSeekersDao.approveJobSeeker(jobSeekerById);

            JobSeekerGeneralResponseDTO jobSeekerGeneralResponseDTO = new JobSeekerGeneralResponseDTO(MAPPER.toJobSeekerDTO(jobSeekerById));

            return jobSeekerGeneralResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in JobSeekersServiceImpl.approveJobSeeker : {}", e.getMessage());
            throw new UsersException(ErrorCode.JOB_SEEKER_APPROVE_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional
    @KafkaHelper(topicName = Constants.USER_SERVICE_TOPIC, entityNameForKafka = Constants.JOBSEEKER_KAFKA, operationName = Constants.DELETE)
    public CommonResponseDTO deleteJobSeeker(Long jobSeekerId) {
        try {
            jobSeekersDao.deleteJobSeeker(jobSeekerId);

            CommonResponseDTO commonResponseDTO = new CommonResponseDTO(Constants.STATUS_OK, Constants.OK);

            return commonResponseDTO;
        } catch (Exception e) {
            LOGGER.error("An Error has been occured in JobSeekersServiceImpl.deleteJobSeeker : {}", e.getMessage());
            throw new UsersException(ErrorCode.JOB_SEEKER_DELETE_ERROR, e.getMessage());
        }
    }
}
