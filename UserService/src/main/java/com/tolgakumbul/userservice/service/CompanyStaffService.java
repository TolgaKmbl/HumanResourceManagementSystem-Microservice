package com.tolgakumbul.userservice.service;

import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffGeneralResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffListResponseDTO;

public interface CompanyStaffService {

    CompanyStaffListResponseDTO getAllCompanyStaff();

    CompanyStaffGeneralResponseDTO getCompanyStaffById(Long companyStaffId);

    CompanyStaffGeneralResponseDTO getCompanyStaffByName(String firstName, String lastName);

    CompanyStaffGeneralResponseDTO insertCompanyStaff(CompanyStaffDTO companyStaffDTO);

    CommonResponseDTO deleteCompanyStaff(Long companyStaffId);

    CompanyStaffGeneralResponseDTO approveCompanyStaff(Long companyStaffId);
}
