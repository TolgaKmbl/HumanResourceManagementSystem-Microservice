package com.tolgakumbul.userservice.dao.model;

import com.tolgakumbul.userservice.model.common.PageableDTO;
import lombok.Data;

@Data
public class EmployersByCompanyNameRequest {

    private String companyName;
    private PageableDTO pageable;

}
