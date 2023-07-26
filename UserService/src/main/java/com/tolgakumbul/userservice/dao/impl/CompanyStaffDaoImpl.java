package com.tolgakumbul.userservice.dao.impl;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.constants.QueryConstants;
import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.dao.mapper.CompanyStaffRowMapper;
import com.tolgakumbul.userservice.entity.CompanyStaffEntity;
import com.tolgakumbul.userservice.helper.HazelcastCacheHelper;
import com.tolgakumbul.userservice.helper.aspect.AuditHelper;
import com.tolgakumbul.userservice.helper.aspect.CacheHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CompanyStaffDaoImpl implements CompanyStaffDao {

    private static final Logger LOGGER = LogManager.getLogger(CompanyStaffDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final HazelcastCacheHelper hazelcastCacheHelper;

    public CompanyStaffDaoImpl(JdbcTemplate jdbcTemplate, HazelcastCacheHelper hazelcastCacheHelper) {
        this.jdbcTemplate = jdbcTemplate;
        this.hazelcastCacheHelper = hazelcastCacheHelper;
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    @CacheHelper(mapName = "companyStaffListMap", keyName = "AllCompanyStaff")
    public List<CompanyStaffEntity> getAllCompanyStaff() {
        try {
            List<CompanyStaffEntity> companyStaffEntityList = jdbcTemplate.query(QueryConstants.SELECT_ALL_COMPANY_STAFF_QUERY, new CompanyStaffRowMapper());
            return companyStaffEntityList;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getAllCompanyStaff : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public CompanyStaffEntity getCompanyStaffById(Long companyStaffId) {
        try {
            return jdbcTemplate.queryForObject(QueryConstants.SELECT_COMPANY_STAFF_BY_ID_QUERY,
                    new CompanyStaffRowMapper(),
                    companyStaffId);
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffById : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public CompanyStaffEntity getCompanyStaffByName(String firstName, String lastName) {
        try {
            return jdbcTemplate.queryForObject(QueryConstants.SELECT_COMPANY_STAFF_BY_NAME_QUERY,
                    new CompanyStaffRowMapper(),
                    firstName, lastName);
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @AuditHelper(sqlQuery = Constants.SQL_INSERT)
    public Integer insertCompanyStaff(CompanyStaffEntity companyStaffEntity) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.INSERT_COMPANY_STAFF_QUERY,
                    companyStaffEntity.getUserId(),
                    companyStaffEntity.getFirstName(),
                    companyStaffEntity.getLastName(),
                    companyStaffEntity.getIsApproved(),
                    null,
                    companyStaffEntity.getCreatedBy(),
                    companyStaffEntity.getCreatedAt(),
                    companyStaffEntity.getUpdatedBy(),
                    companyStaffEntity.getUpdatedAt());
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.insertCompanyStaff : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /*TODO: Restrict the updatable columns */
    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @AuditHelper(sqlQuery = Constants.SQL_UPDATE)
    public Integer updateCompanyStaff(CompanyStaffEntity companyStaffEntity) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.UPDATE_COMPANY_STAFF_QUERY,
                    companyStaffEntity.getFirstName(),
                    companyStaffEntity.getLastName(),
                    companyStaffEntity.getIsApproved(),
                    companyStaffEntity.getApprovalDate(),
                    companyStaffEntity.getUpdatedBy(),
                    companyStaffEntity.getUpdatedAt(),
                    companyStaffEntity.getUserId());
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.updateCompanyStaff : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /*TODO: Create an IS_DELETED COLUMN AND UPDATE IT INSTEAD OF DELETING DATA*/
    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer deleteCompanyStaff(Long companyStaffId) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.DELETE_COMPANY_STAFF_QUERY,
                    companyStaffId);
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.deleteCompanyStaff : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @AuditHelper(sqlQuery = Constants.SQL_UPDATE)
    public Integer approveCompanyStaff(CompanyStaffEntity companyStaffEntity) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.APPROVE_COMPANY_STAFF_QUERY,
                    companyStaffEntity.getIsApproved(),
                    LocalDateTime.now(),
                    companyStaffEntity.getUpdatedBy(),
                    companyStaffEntity.getUpdatedAt(),
                    companyStaffEntity.getUserId());
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
