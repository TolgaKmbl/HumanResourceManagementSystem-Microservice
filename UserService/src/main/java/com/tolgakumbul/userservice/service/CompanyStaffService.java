package com.tolgakumbul.userservice.service;

import com.tolgakumbul.userservice.model.CompanyStaffDTO;

import java.util.List;

public interface CompanyStaffService {

    List<CompanyStaffDTO> getAllCompanyStaff();

    CompanyStaffDTO getCompanyStaffById(Long companyStaffId);

    CompanyStaffDTO getCompanyStaffByName(String firstName, String lastName);

    CompanyStaffDTO insertCompanyStaff(CompanyStaffDTO companyStaffDTO);
}
