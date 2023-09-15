package com.tolgakumbul.userservice.dao;


import com.tolgakumbul.userservice.dao.model.ListRequest;
import com.tolgakumbul.userservice.entity.CompanyStaffEntity;

import java.util.List;

public interface CompanyStaffDao {

    List<CompanyStaffEntity> getAllCompanyStaff(ListRequest listRequest);

    CompanyStaffEntity getCompanyStaffById(Long companyStaffId);

    CompanyStaffEntity getCompanyStaffByName(String firstName, String lastName);

    Integer insertCompanyStaff(CompanyStaffEntity companyStaffEntity);

    Integer updateCompanyStaff(CompanyStaffEntity companyStaffEntity);

    Integer deleteCompanyStaff(Long companyStaffId);

    Integer approveCompanyStaff(CompanyStaffEntity companyStaffEntity);

    Long getLatestUserId();

    Long getTotalRowCount();

}
