package com.tolgakumbul.userservice.dao.impl;

import com.tolgakumbul.userservice.constants.QueryConstants;
import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.dao.mapper.CompanyStaffRowMapper;
import com.tolgakumbul.userservice.entity.CompanyStaff;
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
            LOGGER.error("An Error has been occured in CompanyStaffDaoImpl.getAllCompanyStaff : {}", e.getMessage());
            return new ArrayList<>();
        }

    }

}
