package com.tolgakumbul.userservice.service;

import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import com.tolgakumbul.userservice.model.employers.*;

public interface EmployersService {

    EmployersListResponseDTO getAllEmployers(GetAllEmployersRequestDTO requestDTO);

    EmployersGeneralResponseDTO getEmployerById(Long employerId);

    EmployersListResponseDTO getEmployersByCompanyName(EmployersByCompanyNameRequestDTO requestDTO);

    EmployersGeneralResponseDTO updateEmployer(EmployersDTO employersDTO);

    EmployersGeneralResponseDTO insertEmployer(EmployersDTO employersDTO);

    CommonResponseDTO deleteEmployer(Long employerId);

}
