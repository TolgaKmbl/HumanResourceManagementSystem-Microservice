package com.tolgakumbul.userservice.model.companystaff;

import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CompanyStaffGeneralResponseDTO {

    private CompanyStaffDTO companyStaffData;
    private CommonResponseDTO commonResponse;

}
