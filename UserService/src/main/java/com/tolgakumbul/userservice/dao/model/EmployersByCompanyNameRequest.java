package com.tolgakumbul.userservice.dao.model;

import lombok.Data;

@Data
public class EmployersByCompanyNameRequest {

    private String companyName;
    private Pageable pageable;

}
