package com.tolgakumbul.userservice.dao;


import com.tolgakumbul.proto.CommonProto.CommonResponse;
import com.tolgakumbul.userservice.entity.CompanyStaff;
import com.tolgakumbul.userservice.model.CommonResponseDTO;

import java.util.List;

public interface CompanyStaffDao {

    List<CompanyStaff> getAllCompanyStaff();

    CompanyStaff getCompanyStaffById(Long companyStaffId);

    CompanyStaff getCompanyStaffByName(String firstName, String lastName);

    CommonResponseDTO insertCompanyStaff(CompanyStaff companyStaff);

}
