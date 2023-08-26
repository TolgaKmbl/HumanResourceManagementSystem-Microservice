package com.tolgakumbul.userservice.dao.impl;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.constants.QueryConstants;
import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.dao.mapper.CompanyStaffRowMapper;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.CompanyStaffEntity;
import com.tolgakumbul.userservice.helper.HazelcastCacheHelper;
import com.tolgakumbul.userservice.helper.aspect.AuditHelper;
import com.tolgakumbul.userservice.util.QueryUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    //@CacheHelper(mapName = "companyStaffListMap", keyName = "AllCompanyStaff") /*Removed due to the pagination*/
    public List<CompanyStaffEntity> getAllCompanyStaff(ListRequest listRequest) {
        try {
            List<Object> params = new ArrayList<>();
            StringBuilder editedSql = QueryUtil.addPageableQuery(new StringBuilder(QueryConstants.SELECT_ALL_COMPANY_STAFF_QUERY), params, listRequest.getPageable());
            List<CompanyStaffEntity> companyStaffEntityList = jdbcTemplate.query(editedSql.toString(), new CompanyStaffRowMapper(), params.toArray(new Object[0]));
            return companyStaffEntityList;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getAllCompanyStaff : {}", e.getMessage());
            return new ArrayList<>();
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
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffById : {}", e.getMessage());
            return new CompanyStaffEntity();
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
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
            return new CompanyStaffEntity();
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
                    companyStaffEntity.getUpdatedAt(),
                    "N");
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

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer deleteCompanyStaff(Long companyStaffId) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.DELETE_COMPANY_STAFF_QUERY,
                    "Y",
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

    @Override
    public Long getLatestUserId() {
        try {
            Long userId = jdbcTemplate.queryForObject(QueryConstants.GET_LATEST_COMPANY_STAFF_ID_QUERY, Long.class);
            return userId;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getLatestUserId : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
