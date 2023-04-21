package com.tolgakumbul.userservice.model.companystaff;

import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CompanyStaffListResponseDTO {

    private List<CompanyStaffDTO> companyStaffList;
    private CommonResponseDTO commonResponse;
}
