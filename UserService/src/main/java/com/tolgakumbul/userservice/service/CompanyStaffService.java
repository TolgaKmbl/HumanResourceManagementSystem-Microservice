package com.tolgakumbul.userservice.service;

import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.userservice.model.CommonResponseDTO;
import com.tolgakumbul.userservice.model.CompanyStaffDTO;

import java.util.List;

public interface CompanyStaffService {

    List<CompanyStaffDTO> getAllCompanyStaff();

    CompanyStaffDTO getCompanyStaffById(Long companyStaffId);

    CompanyStaffDTO getCompanyStaffByName(String firstName, String lastName);

    CommonResponseDTO insertCompanyStaff(CompanyStaffDTO companyStaffDTO);
}
