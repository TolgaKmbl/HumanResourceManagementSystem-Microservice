package com.tolgakumbul.userservice.dao.impl;

import com.tolgakumbul.userservice.constants.QueryConstants;
import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.dao.mapper.CompanyStaffRowMapper;
import com.tolgakumbul.userservice.entity.CompanyStaffEntity;
import com.tolgakumbul.userservice.exception.UsersException;
import com.tolgakumbul.userservice.model.companystaff.IsApprovedEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyStaffDaoImpl implements CompanyStaffDao {

    private static final Logger LOGGER = LogManager.getLogger(CompanyStaffDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public CompanyStaffDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public List<CompanyStaffEntity> getAllCompanyStaff() {
        try {
            return jdbcTemplate.query(QueryConstants.SELECT_ALL_COMPANY_STAFF_QUERY, new CompanyStaffRowMapper());
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getAllCompanyStaff : {}", e.getMessage());
            return new ArrayList<>();
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
            return null;
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
            return null;
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer insertCompanyStaff(CompanyStaffEntity companyStaffEntity) {
        try {
            return jdbcTemplate.update(QueryConstants.INSERT_COMPANY_STAFF_QUERY,
                    companyStaffEntity.getUserId(),
                    companyStaffEntity.getFirstName(),
                    companyStaffEntity.getLastName(),
                    companyStaffEntity.getIsApproved(),
                    null);
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
            throw new UsersException("ERRDAO001", e.getMessage());
        }
    }

    /*TODO: Create an IS_DELETED COLUMN AND UPDATE IT INSTEAD OF DELETING DATA*/
    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer deleteCompanyStaff(Long companyStaffId) {
        try {
            return jdbcTemplate.update(QueryConstants.DELETE_COMPANY_STAFF_QUERY,
                    companyStaffId);
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
            throw new UsersException("ERRDAO001", e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer approveCompanyStaff(Long companyStaffId) {
        try {
            return jdbcTemplate.update(QueryConstants.UPDATE_COMPANY_STAFF_QUERY,
                    IsApprovedEnum.ACTIVE.getTextType(),
                    companyStaffId);
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
            throw new UsersException("ERRDAO001", e.getMessage());
        }
    }

}
