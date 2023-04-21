package com.tolgakumbul.userservice.dao;


import com.tolgakumbul.userservice.entity.CompanyStaff;

import java.util.List;

public interface CompanyStaffDao {

    List<CompanyStaff> getAllCompanyStaff();

    CompanyStaff getCompanyStaffById(Long companyStaffId);

    CompanyStaff getCompanyStaffByName(String firstName, String lastName);

    Integer insertCompanyStaff(CompanyStaff companyStaff);

    Integer deleteCompanyStaff(Long companyStaffId);

    Integer approveCompanyStaff(Long companyStaffId);

}
