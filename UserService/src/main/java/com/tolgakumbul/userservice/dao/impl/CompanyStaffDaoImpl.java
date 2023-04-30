package com.tolgakumbul.userservice.dao.impl;

import com.tolgakumbul.userservice.constants.QueryConstants;
import com.tolgakumbul.userservice.dao.CompanyStaffDao;
import com.tolgakumbul.userservice.dao.mapper.CompanyStaffRowMapper;
import com.tolgakumbul.userservice.entity.CompanyStaffEntity;
import com.tolgakumbul.userservice.helper.HazelcastCacheHelper;
import com.tolgakumbul.userservice.helper.model.hazelcast.HazelcastCacheModel;
import com.tolgakumbul.userservice.model.companystaff.IsApprovedEnum;
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
    public List<CompanyStaffEntity> getAllCompanyStaff() {
        try {
            HazelcastCacheModel hazelcastCacheModel = new HazelcastCacheModel();
            hazelcastCacheModel.setKeyName("AllCompanyStaff");
            hazelcastCacheModel.setMapName("companyStaffListMap");
            List<CompanyStaffEntity> companyStaffEntityList = (List<CompanyStaffEntity>) hazelcastCacheHelper.get(hazelcastCacheModel);
            if (CollectionUtils.isEmpty(companyStaffEntityList)) {
                LOGGER.info("GetAllCompanyStaff.CompanyStaffEntityList has not found on cache");
                companyStaffEntityList = jdbcTemplate.query(QueryConstants.SELECT_ALL_COMPANY_STAFF_QUERY, new CompanyStaffRowMapper());
                hazelcastCacheModel.setCachedObject(companyStaffEntityList);
                hazelcastCacheHelper.put(hazelcastCacheModel);
                LOGGER.info("GetAllCompanyStaff.CompanyStaffEntityList put on cache {}", hazelcastCacheModel);
                return companyStaffEntityList;
            }
            LOGGER.info("GetAllCompanyStaff.CompanyStaffEntityList found on cache {}", companyStaffEntityList);
            return companyStaffEntityList;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getAllCompanyStaff : {}", e.getMessage());
            return new ArrayList<>();
        }

    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public CompanyStaffEntity getCompanyStaffById(Long companyStaffId) {
        try {
            HazelcastCacheModel hazelcastCacheModel = new HazelcastCacheModel();
            hazelcastCacheModel.setKeyName("CompanyStaffById" + companyStaffId);
            hazelcastCacheModel.setMapName("companyStaffByIdMap");
            CompanyStaffEntity companyStaffEntity = (CompanyStaffEntity) hazelcastCacheHelper.get(hazelcastCacheModel);
            if (ObjectUtils.isEmpty(companyStaffEntity)) {
                LOGGER.info("CompanyStaffById.CompanyStaffEntity has not found on cache");
                companyStaffEntity = jdbcTemplate.queryForObject(QueryConstants.SELECT_COMPANY_STAFF_BY_ID_QUERY,
                        new CompanyStaffRowMapper(),
                        companyStaffId);
                hazelcastCacheModel.setCachedObject(companyStaffEntity);
                hazelcastCacheHelper.put(hazelcastCacheModel);
                LOGGER.info("CompanyStaffById.CompanyStaffEntity put on cache {}", hazelcastCacheModel);
                return companyStaffEntity;
            }
            LOGGER.info("CompanyStaffById.CompanyStaffEntity found on cache {}", companyStaffEntity);
            return companyStaffEntity;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffById : {}", e.getMessage());
            return new CompanyStaffEntity();
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_READ)
    public CompanyStaffEntity getCompanyStaffByName(String firstName, String lastName) {
        try {
            HazelcastCacheModel hazelcastCacheModel = new HazelcastCacheModel();
            hazelcastCacheModel.setKeyName("CompanyStaffByName" + firstName + lastName);
            hazelcastCacheModel.setMapName("companyStaffByNameMap");
            CompanyStaffEntity companyStaffEntity = (CompanyStaffEntity) hazelcastCacheHelper.get(hazelcastCacheModel);
            if (ObjectUtils.isEmpty(companyStaffEntity)) {
                LOGGER.info("CompanyStaffByName.CompanyStaffEntity has not found on cache");
                companyStaffEntity = jdbcTemplate.queryForObject(QueryConstants.SELECT_COMPANY_STAFF_BY_NAME_QUERY,
                        new CompanyStaffRowMapper(),
                        firstName, lastName);
                hazelcastCacheModel.setCachedObject(companyStaffEntity);
                hazelcastCacheHelper.put(hazelcastCacheModel);
                LOGGER.info("CompanyStaffByName.CompanyStaffEntity put on cache {}", hazelcastCacheModel);
                return companyStaffEntity;
            }
            LOGGER.info("CompanyStaffByName.CompanyStaffEntity found on cache {}", companyStaffEntity);
            return companyStaffEntity;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
            return new CompanyStaffEntity();
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer insertCompanyStaff(CompanyStaffEntity companyStaffEntity) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.INSERT_COMPANY_STAFF_QUERY,
                    companyStaffEntity.getUserId(),
                    companyStaffEntity.getFirstName(),
                    companyStaffEntity.getLastName(),
                    companyStaffEntity.getIsApproved(),
                    null);
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
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
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Lock(LockMode.PESSIMISTIC_WRITE)
    public Integer approveCompanyStaff(Long companyStaffId) {
        try {
            int affectedRowCount = jdbcTemplate.update(QueryConstants.UPDATE_COMPANY_STAFF_QUERY,
                    IsApprovedEnum.ACTIVE.getTextType(),
                    companyStaffId);
            hazelcastCacheHelper.removeAll();
            return affectedRowCount;
        } catch (Exception e) {
            LOGGER.error("An Error has been occurred in CompanyStaffDaoImpl.getCompanyStaffByName : {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
