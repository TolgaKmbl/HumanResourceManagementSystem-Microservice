package com.tolgakumbul.userservice.dao;

import com.tolgakumbul.userservice.dao.model.EmployersByCompanyNameRequest;
import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.EmployersEntity;

import java.util.List;

public interface EmployersDao {

    List<EmployersEntity> getAllEmployers(ListRequest listRequest);

    EmployersEntity getEmployerById(Long employerId);

    List<EmployersEntity> getEmployersByCompanyName(EmployersByCompanyNameRequest daoRequest);

    Integer updateEmployer(EmployersEntity employersEntity);

    Integer insertEmployer(EmployersEntity employersEntity);

    Integer deleteEmployer(Long employerId);

    Long getLatestUserId();

    Long getTotalRowCount();

}
