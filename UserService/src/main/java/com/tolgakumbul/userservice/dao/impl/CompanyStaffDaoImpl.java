package com.tolgakumbul.userservice.dao.impl;

import com.tolgakumbul.userservice.constants.QueryConstants;
import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.dao.mapper.CompanyStaffRowMapper;
import com.tolgakumbul.userservice.entity.CompanyStaff;
import com.tolgakumbul.userservice.model.CommonResponseDTO;
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

    private static Logger LOGGER = LogManager.getLogger(CompanyStaffDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public CompanyStaffDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public List<CompanyStaff> getAllCompanyStaff() {
        try {
            return jdbcTemplate.query(QueryConstants.SELECT_ALL_COMPANY_STAFF_QUERY, new CompanyStaffRowMapper());
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getAllCompanyStaff : {}", e.getMessage());
            return new ArrayList<>();
        }

    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public CompanyStaff getCompanyStaffById(Long companyStaffId) {
        try {
            return jdbcTemplate.queryForObject(QueryConstants.SELECT_COMPANY_STAFF_BY_ID_QUERY,
                    new Object[]{companyStaffId},
                    new CompanyStaffRowMapper());
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffById : {}", e.getMessage());
            return null;
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public CompanyStaff getCompanyStaffByName(String firstName, String lastName) {
        try {
            return jdbcTemplate.queryForObject(QueryConstants.SELECT_COMPANY_STAFF_BY_NAME_QUERY,
                    new Object[]{firstName, lastName},
                    new CompanyStaffRowMapper());
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
            return null;
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public CommonResponseDTO insertCompanyStaff(CompanyStaff companyStaff) {
        try {
            int updatedRow = jdbcTemplate.update(QueryConstants.INSERT_COMPANY_STAFF_QUERY,
                    companyStaff.getUserId(),
                    companyStaff.getFirstName(),
                    companyStaff.getLastName(),
                    companyStaff.getIsApproved(),
                    null);
            if(updatedRow > 0) {
                return new CommonResponseDTO(200, "OK");
            }
            return new CommonResponseDTO(500, "Could not insert data!");
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
            return new CommonResponseDTO(500, e.getMessage());
        }
    }

}
