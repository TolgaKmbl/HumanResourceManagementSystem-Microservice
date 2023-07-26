package com.tolgakumbul.userservice.dao.impl;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.constants.QueryConstants;
import com.tolgakumbul.userservice.dao.EmployersDao;
import com.tolgakumbul.userservice.dao.mapper.EmployersRowMapper;
import com.tolgakumbul.userservice.entity.EmployersEntity;
import com.tolgakumbul.userservice.helper.HazelcastCacheHelper;
import com.tolgakumbul.userservice.helper.aspect.AuditHelper;
import com.tolgakumbul.userservice.helper.aspect.CacheHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployersDaoImpl implements EmployersDao {

    private static final Logger LOGGER = LogManager.getLogger(EmployersDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final HazelcastCacheHelper hazelcastCacheHelper;

    public EmployersDaoImpl(JdbcTemplate jdbcTemplate, HazelcastCacheHelper hazelcastCacheHelper) {
        this.jdbcTemplate = jdbcTemplate;
        this.hazelcastCacheHelper = hazelcastCacheHelper;
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    @CacheHelper(mapName = "employerListMap", keyName = "AllEmployers")
    public List<EmployersEntity> getAllEmployers() {
        try {
            List<EmployersEntity> employersEntityList = jdbcTemplate.query(QueryConstants.SELECT_ALL_EMPLOYERS_QUERY, new EmployersRowMapper());
            return employersEntityList;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getAllEmployers : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public EmployersEntity getEmployerById(Long employerId) {
        try {
            return jdbcTemplate.queryForObject(QueryConstants.SELECT_EMPLOYER_BY_ID_QUERY,
                    new EmployersRowMapper(),
                    employerId);
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getEmployerById : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public List<EmployersEntity> getEmployersByCompanyName(String companyName) {
        try {
            return jdbcTemplate.query(QueryConstants.SELECT_EMPLOYERS_BY_COMPANY_NAME_QUERY,
                    new EmployersRowMapper(),
                    companyName);
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getEmployersByCompanyName : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @AuditHelper(sqlQuery = Constants.SQL_UPDATE)
    public Integer updateEmployer(EmployersEntity employersEntity) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.UPDATE_EMPLOYER_QUERY,
                    employersEntity.getCompanyName(),
                    employersEntity.getWebsite(),
                    employersEntity.getPhoneNum(),
                    employersEntity.getCompanyImgUrl(),
                    employersEntity.getUpdatedBy(),
                    employersEntity.getUpdatedAt(),
                    employersEntity.getUserId());
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.updateEmployer : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @AuditHelper(sqlQuery = Constants.SQL_INSERT)
    public Integer insertEmployer(EmployersEntity employersEntity) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.INSERT_EMPLOYER_QUERY,
                    employersEntity.getUserId(),
                    employersEntity.getCompanyName(),
                    employersEntity.getWebsite(),
                    employersEntity.getPhoneNum(),
                    employersEntity.getCompanyImgUrl(),
                    employersEntity.getCreatedBy(),
                    employersEntity.getCreatedAt(),
                    employersEntity.getUpdatedBy(),
                    employersEntity.getUpdatedAt());
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.insertEmployer : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /*TODO: Create an IS_DELETED COLUMN AND UPDATE IT INSTEAD OF DELETING DATA*/
    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer deleteEmployer(Long employerId) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.DELETE_EMPLOYER_QUERY,
                    employerId);
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.deleteEmployer : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
