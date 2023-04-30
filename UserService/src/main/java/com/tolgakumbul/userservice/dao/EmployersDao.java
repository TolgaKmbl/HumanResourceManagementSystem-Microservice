package com.tolgakumbul.userservice.dao;

import com.tolgakumbul.userservice.entity.EmployersEntity;

import java.util.List;

public interface EmployersDao {

    List<EmployersEntity> getAllEmployers();

    EmployersEntity getEmployerById(Long employerId);

    List<EmployersEntity> getEmployersByCompanyName(String companyName);

    Integer updateEmployer(EmployersEntity employersEntity);

    Integer insertEmployer(EmployersEntity employersEntity);

    Integer deleteEmployer(Long employerId);

}
