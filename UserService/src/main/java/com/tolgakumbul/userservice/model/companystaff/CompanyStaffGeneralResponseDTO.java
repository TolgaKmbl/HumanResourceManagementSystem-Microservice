package com.tolgakumbul.userservice.model.companystaff;

import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CompanyStaffGeneralResponseDTO implements Serializable {

    private static final long serialVersionUID = -8617257709520059958L;

    private CompanyStaffDTO companyStaffData;
    private CommonResponseDTO commonResponse;

}
