package com.tolgakumbul.userservice.dao.impl;

import com.tolgakumbul.userservice.constants.Constants;
import com.tolgakumbul.userservice.constants.QueryConstants;
import com.tolgakumbul.userservice.dao.EmployersDao;
import com.tolgakumbul.userservice.dao.mapper.EmployersRowMapper;
import com.tolgakumbul.userservice.dao.model.EmployersByCompanyNameRequest;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.EmployersEntity;
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

import java.util.ArrayList;
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
    //@CacheHelper(mapName = "employerListMap", keyName = "AllEmployers") /*Removed due to the pagination*/
    public List<EmployersEntity> getAllEmployers(ListRequest listRequest) {
        try {
            List<Object> params = new ArrayList<>();
            StringBuilder editedSql = QueryUtil.addPageableQuery(new StringBuilder(QueryConstants.SELECT_ALL_EMPLOYERS_QUERY), params, listRequest.getPageable());
            List<EmployersEntity> employersEntityList = jdbcTemplate.query(editedSql.toString(), new EmployersRowMapper(), params.toArray(new Object[0]));
            return employersEntityList;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getAllEmployers : {}", e.getMessage());
            return new ArrayList<>();
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
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getEmployerById : {}", e.getMessage());
            return new EmployersEntity();
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getEmployerById : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public List<EmployersEntity> getEmployersByCompanyName(EmployersByCompanyNameRequest daoRequest) {
        try {
            List<Object> params = new ArrayList<>();
            params.add(daoRequest.getCompanyName());
            StringBuilder editedSql = QueryUtil.addPageableQuery(new StringBuilder(QueryConstants.SELECT_EMPLOYERS_BY_COMPANY_NAME_QUERY), params, daoRequest.getPageable());
            List<EmployersEntity> employersEntityList = jdbcTemplate.query(editedSql.toString(), new EmployersRowMapper(), params.toArray(new Object[0]));
            return employersEntityList;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getEmployersByCompanyName : {}", e.getMessage());
            return new ArrayList<>();
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
                    employersEntity.getUpdatedAt(),
                    "N");
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.insertEmployer : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer deleteEmployer(Long employerId) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.DELETE_EMPLOYER_QUERY,
                    "Y",
                    employerId);
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.deleteEmployer : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Long getLatestUserId() {
        try {
            Long userId = jdbcTemplate.queryForObject(QueryConstants.GET_LATEST_EMPLOYER_ID_QUERY, Long.class);
            return userId;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getLatestUserId : {}", e.getMessage());
            return 0L;
        }
    }

    @Override
    public Long getTotalRowCount() {
        try {
            StringBuilder sb = new StringBuilder(QueryConstants.GET_TOTAL_ROW_COUNT);
            sb.append("EMPLOYERS");
            Long totalRowCount = jdbcTemplate.queryForObject(sb.toString(), Long.class);
            return totalRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getTotalRowCount : {}", e.getMessage());
            return 0L;
        }
    }
}
