package com.tolgakumbul.userservice.model.employers;

import com.tolgakumbul.userservice.model.common.PageableDTO;
import lombok.Data;

@Data
public class EmployersByCompanyNameRequestDTO {

    private String companyName;
    private PageableDTO pageable;
}
