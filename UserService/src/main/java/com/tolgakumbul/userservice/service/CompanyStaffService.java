package com.tolgakumbul.userservice.service;

import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffGeneralResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.CompanyStaffListResponseDTO;
import com.tolgakumbul.userservice.model.companystaff.GetAllCompanyStaffRequestDTO;

public interface CompanyStaffService {

    CompanyStaffListResponseDTO getAllCompanyStaff(GetAllCompanyStaffRequestDTO requestDTO);

    CompanyStaffGeneralResponseDTO getCompanyStaffById(Long companyStaffId);

    CompanyStaffGeneralResponseDTO getCompanyStaffByName(String firstName, String lastName);

    CompanyStaffGeneralResponseDTO insertCompanyStaff(CompanyStaffDTO companyStaffDTO);

    CompanyStaffGeneralResponseDTO updateCompanyStaff(CompanyStaffDTO companyStaffDTO);

    CommonResponseDTO deleteCompanyStaff(Long companyStaffId);

    CompanyStaffGeneralResponseDTO approveCompanyStaff(Long companyStaffId);
}
