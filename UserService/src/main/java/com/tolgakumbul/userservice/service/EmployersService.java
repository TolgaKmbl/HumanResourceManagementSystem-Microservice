package com.tolgakumbul.userservice.service;

import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersDTO;
import com.tolgakumbul.userservice.model.employers.EmployersGeneralResponseDTO;
import com.tolgakumbul.userservice.model.employers.EmployersListResponseDTO;

public interface EmployersService {

    EmployersListResponseDTO getAllEmployers();

    EmployersGeneralResponseDTO getEmployerById(Long employerId);

    EmployersListResponseDTO getEmployersByCompanyName(String companyName);

    EmployersGeneralResponseDTO updateEmployer(EmployersDTO employersDTO);

    EmployersGeneralResponseDTO insertEmployer(EmployersDTO employersDTO);

    CommonResponseDTO deleteEmployer(Long employerId);

}
