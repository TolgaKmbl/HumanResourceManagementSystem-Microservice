package com.tolgakumbul.userservice.dao.impl;

import com.tolgakumbul.userservice.constants.QueryConstants;
import com.tolgakumbul.userservice.dao.EmployersDao;
import com.tolgakumbul.userservice.dao.mapper.EmployersRowMapper;
import com.tolgakumbul.userservice.entity.EmployersEntity;
import com.tolgakumbul.userservice.helper.HazelcastCacheHelper;
import com.tolgakumbul.userservice.helper.model.hazelcast.HazelcastCacheModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

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
    public List<EmployersEntity> getAllEmployers() {
        try {
            HazelcastCacheModel hazelcastCacheModel = new HazelcastCacheModel();
            hazelcastCacheModel.setKeyName("AllEmployers");
            hazelcastCacheModel.setMapName("employerListMap");
            List<EmployersEntity> employersEntityList = (List<EmployersEntity>) hazelcastCacheHelper.get(hazelcastCacheModel);
            if (CollectionUtils.isEmpty(employersEntityList)) {
                LOGGER.info("GetAllEmployers.EmployersEntityList has not found on cache");
                employersEntityList = jdbcTemplate.query(QueryConstants.SELECT_ALL_EMPLOYERS_QUERY, new EmployersRowMapper());
                hazelcastCacheModel.setCachedObject(employersEntityList);
                hazelcastCacheHelper.put(hazelcastCacheModel);
                LOGGER.info("GetAllEmployers.EmployersEntityList put on cache {}", hazelcastCacheModel);
                return employersEntityList;
            }
            LOGGER.info("GetAllEmployers.EmployersEntityList found on cache {}", employersEntityList);
            return employersEntityList;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getAllEmployers : {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public EmployersEntity getEmployerById(Long employerId) {
        try {
            HazelcastCacheModel hazelcastCacheModel = new HazelcastCacheModel();
            hazelcastCacheModel.setKeyName("EmployerById" + employerId);
            hazelcastCacheModel.setMapName("employerByIdMap");
            EmployersEntity employerEntity = (EmployersEntity) hazelcastCacheHelper.get(hazelcastCacheModel);
            if (ObjectUtils.isEmpty(employerEntity)) {
                LOGGER.info("EmployerById.EmployersEntity has not found on cache");
                employerEntity = jdbcTemplate.queryForObject(QueryConstants.SELECT_EMPLOYER_BY_ID_QUERY,
                        new EmployersRowMapper(),
                        employerId);
                hazelcastCacheModel.setCachedObject(employerEntity);
                hazelcastCacheHelper.put(hazelcastCacheModel);
                LOGGER.info("EmployerById.EmployersEntity put on cache {}", hazelcastCacheModel);
                return employerEntity;
            }
            LOGGER.info("EmployerById.EmployersEntity found on cache {}", employerEntity);
            return employerEntity;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getEmployerById : {}", e.getMessage());
            return new EmployersEntity();
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public List<EmployersEntity> getEmployersByCompanyName(String companyName) {
        try {
            HazelcastCacheModel hazelcastCacheModel = new HazelcastCacheModel();
            hazelcastCacheModel.setKeyName("EmployersByCompanyName" + companyName);
            hazelcastCacheModel.setMapName("employerByNameMap");
            List<EmployersEntity> employersEntityList = (List<EmployersEntity>) hazelcastCacheHelper.get(hazelcastCacheModel);
            if (ObjectUtils.isEmpty(employersEntityList)) {
                LOGGER.info("EmployersByCompanyName.EmployersEntityList has not found on cache");
                employersEntityList = jdbcTemplate.query(QueryConstants.SELECT_EMPLOYERS_BY_COMPANY_NAME_QUERY,
                        new EmployersRowMapper(),
                        companyName);
                hazelcastCacheModel.setCachedObject(employersEntityList);
                hazelcastCacheHelper.put(hazelcastCacheModel);
                LOGGER.info("EmployersByCompanyName.EmployersEntityList put on cache {}", hazelcastCacheModel);
                return employersEntityList;
            }
            LOGGER.info("EmployersByCompanyName.EmployersEntityList found on cache {}", employersEntityList);
            return employersEntityList;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.getEmployersByCompanyName : {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer updateEmployer(EmployersEntity employersEntity) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.UPDATE_EMPLOYER_QUERY,
                    employersEntity.getCompanyName(),
                    employersEntity.getWebsite(),
                    employersEntity.getPhoneNum(),
                    employersEntity.getCompanyImgUrl(),
                    employersEntity.getUserId());
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.updateEmployer : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer insertEmployer(EmployersEntity employersEntity) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.INSERT_EMPLOYER_QUERY,
                    employersEntity.getUserId(),
                    employersEntity.getCompanyName(),
                    employersEntity.getWebsite(),
                    employersEntity.getPhoneNum(),
                    employersEntity.getCompanyImgUrl());
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
                    employerId);
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in EmployersDaoImpl.deleteEmployer : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
