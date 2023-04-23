package com.tolgakumbul.userservice.model.companystaff;

import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CompanyStaffListResponseDTO implements Serializable {

    private static final long serialVersionUID = 8450464051342688386L;

    private List<CompanyStaffDTO> companyStaffList;
    private CommonResponseDTO commonResponse;
}
