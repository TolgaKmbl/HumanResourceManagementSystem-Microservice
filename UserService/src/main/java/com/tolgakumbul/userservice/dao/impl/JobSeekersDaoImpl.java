package com.tolgakumbul.userservice.dao.impl;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.constants.QueryConstants;
import com.tolgakumbul.userservice.dao.JobSeekersDao;
import com.tolgakumbul.userservice.dao.mapper.JobSeekersRowMapper;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.JobSeekersEntity;
import com.tolgakumbul.userservice.helper.HazelcastCacheHelper;
import com.tolgakumbul.userservice.helper.aspect.AuditHelper;
import com.tolgakumbul.userservice.util.QueryUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JobSeekersDaoImpl implements JobSeekersDao {


    private static final Logger LOGGER = LogManager.getLogger(JobSeekersDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final HazelcastCacheHelper hazelcastCacheHelper;

    public JobSeekersDaoImpl(JdbcTemplate jdbcTemplate, HazelcastCacheHelper hazelcastCacheHelper) {
        this.jdbcTemplate = jdbcTemplate;
        this.hazelcastCacheHelper = hazelcastCacheHelper;
    }

    @Override
    //@CacheHelper(mapName = "jobseekerListMap", keyName = "AllJobSeekers") /*Removed due to the pagination*/
    public List<JobSeekersEntity> getAllJobSeekers(ListRequest listRequest) {
        try {
            List<Long> params = new ArrayList<>();
            StringBuilder editedSql = QueryUtil.addPageableQuery(new StringBuilder(QueryConstants.SELECT_ALL_JOB_SEEKERS_QUERY), params, listRequest.getPageable());
            List<JobSeekersEntity> jobSeekersEntityList = jdbcTemplate.query(editedSql.toString(), new JobSeekersRowMapper(), params.toArray(new Object[0]));
            return jobSeekersEntityList;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.getAllJobSeekers : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public JobSeekersEntity getJobSeekerById(Long jobSeekerId) {
        try {
            JobSeekersEntity jobSeekersEntity = jdbcTemplate.queryForObject(QueryConstants.SELECT_JOB_SEEKER_BY_ID_QUERY, new JobSeekersRowMapper());
            return jobSeekersEntity;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.getJobSeekerById : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public JobSeekersEntity getJobSeekerByNationalId(String nationalId) {
        try {
            JobSeekersEntity jobSeekersEntity = jdbcTemplate.queryForObject(QueryConstants.SELECT_JOB_SEEKER_BY_NATIONAL_QUERY, new JobSeekersRowMapper());
            return jobSeekersEntity;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.getJobSeekerByNationalId : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public JobSeekersEntity getJobSeekerByName(String firstName, String lastName) {
        try {
            JobSeekersEntity jobSeekersEntity = jdbcTemplate.queryForObject(QueryConstants.SELECT_JOB_SEEKER_BY_NAME_QUERY, new JobSeekersRowMapper());
            return jobSeekersEntity;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.getJobSeekerByNationalId : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @AuditHelper(sqlQuery = Constants.SQL_UPDATE)
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
                    jobSeekersEntity.getUpdatedBy(),
                    jobSeekersEntity.getUpdatedAt(),
                    jobSeekersEntity.getUserId());
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.updateJobSeeker : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @AuditHelper(sqlQuery = Constants.SQL_INSERT)
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
                    jobSeekersEntity.getApprovalDate(),
                    jobSeekersEntity.getCreatedBy(),
                    jobSeekersEntity.getCreatedAt(),
                    jobSeekersEntity.getUpdatedBy(),
                    jobSeekersEntity.getUpdatedAt());
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.insertJobSeeker : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @AuditHelper(sqlQuery = Constants.SQL_UPDATE)
    public Integer approveJobSeeker(JobSeekersEntity jobSeekersEntity) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.APPROVE_JOB_SEEKER_QUERY,
                    jobSeekersEntity.getIsApproved(),
                    LocalDateTime.now(),
                    jobSeekersEntity.getUpdatedBy(),
                    jobSeekersEntity.getUpdatedAt(),
                    jobSeekersEntity.getUserId());
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.approveJobSeeker : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /*TODO: Create an IS_DELETED COLUMN AND UPDATE IT INSTEAD OF DELETING DATA*/
    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer deleteJobSeeker(Long jobSeekerId) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.DELETE_JOB_SEEKER_QUERY,
                    jobSeekerId);
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in JobSeekersImpl.deleteJobSeeker : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
