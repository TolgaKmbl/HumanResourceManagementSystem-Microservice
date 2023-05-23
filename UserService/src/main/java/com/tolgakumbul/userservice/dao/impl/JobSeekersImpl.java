package com.tolgakumbul.userservice.dao.impl;

import com.tolgakumbul.userservice.constants.QueryConstants;
import com.tolgakumbul.userservice.dao.JobSeekersDao;
import com.tolgakumbul.userservice.dao.mapper.JobSeekersRowMapper;
import com.tolgakumbul.userservice.entity.JobSeekersEntity;
import com.tolgakumbul.userservice.helper.HazelcastCacheHelper;
import com.tolgakumbul.userservice.model.companystaff.IsApprovedEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JobSeekersImpl implements JobSeekersDao {


    private static final Logger LOGGER = LogManager.getLogger(JobSeekersImpl.class);

    private final JdbcTemplate jdbcTemplate;
    //TODO: Check unnecessary cache operations
    private final HazelcastCacheHelper hazelcastCacheHelper;

    public JobSeekersImpl(JdbcTemplate jdbcTemplate, HazelcastCacheHelper hazelcastCacheHelper) {
        this.jdbcTemplate = jdbcTemplate;
        this.hazelcastCacheHelper = hazelcastCacheHelper;
    }

    @Override
    public List<JobSeekersEntity> getAllJobSeekers() {
        try {
            List<JobSeekersEntity> jobSeekersEntityList = jdbcTemplate.query(QueryConstants.SELECT_ALL_JOB_SEEKERS_QUERY, new JobSeekersRowMapper());
            return jobSeekersEntityList;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.getAllJobSeekers : {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public JobSeekersEntity getJobSeekerById(Long jobSeekerId) {
        try {
            JobSeekersEntity jobSeekersEntity = jdbcTemplate.queryForObject(QueryConstants.SELECT_JOB_SEEKER_BY_ID_QUERY, new JobSeekersRowMapper());
            return jobSeekersEntity;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.getJobSeekerById : {}", e.getMessage());
            return new JobSeekersEntity();
        }
    }

    @Override
    public JobSeekersEntity getJobSeekerByNationalId(String nationalId) {
        try {
            JobSeekersEntity jobSeekersEntity = jdbcTemplate.queryForObject(QueryConstants.SELECT_JOB_SEEKER_BY_NATIONAL_QUERY, new JobSeekersRowMapper());
            return jobSeekersEntity;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.getJobSeekerByNationalId : {}", e.getMessage());
            return new JobSeekersEntity();
        }
    }

    @Override
    public JobSeekersEntity getJobSeekerByName(String firstName, String lastName) {
        try {
            JobSeekersEntity jobSeekersEntity = jdbcTemplate.queryForObject(QueryConstants.SELECT_JOB_SEEKER_BY_NAME_QUERY, new JobSeekersRowMapper());
            return jobSeekersEntity;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.getJobSeekerByNationalId : {}", e.getMessage());
            return new JobSeekersEntity();
        }
    }

    @Override
    public Integer updateJobSeeker(JobSeekersEntity jobSeekersEntity) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.UPDATE_JOB_SEEKER_QUERY,
                    jobSeekersEntity.getFirstName(),
                    jobSeekersEntity.getLastName(),
                    jobSeekersEntity.getNationalId(),
                    jobSeekersEntity.getBirthDate(),
                    jobSeekersEntity.getCvId(),
                    jobSeekersEntity.getIsApproved(),
                    jobSeekersEntity.getApprovalDate(),
                    jobSeekersEntity.getUserId());
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.updateJobSeeker : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Integer insertJobSeeker(JobSeekersEntity jobSeekersEntity) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.INSERT_JOB_SEEKER_QUERY,
                    jobSeekersEntity.getUserId(),
                    jobSeekersEntity.getFirstName(),
                    jobSeekersEntity.getLastName(),
                    jobSeekersEntity.getNationalId(),
                    jobSeekersEntity.getBirthDate(),
                    jobSeekersEntity.getCvId(),
                    jobSeekersEntity.getIsApproved(),
                    jobSeekersEntity.getApprovalDate());
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.insertJobSeeker : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Integer approveJobSeeker(Long jobSeekerId) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.APPROVE_JOB_SEEKER_QUERY,
                    IsApprovedEnum.ACTIVE.getTextType(),
                    jobSeekerId);
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.approveJobSeeker : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /*TODO: Create an IS_DELETED COLUMN AND UPDATE IT INSTEAD OF DELETING DATA*/
    @Override
    public Integer deleteJobSeeker(Long jobSeekerId) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.DELETE_JOB_SEEKER_QUERY,
                    jobSeekerId);
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.deleteJobSeeker : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
