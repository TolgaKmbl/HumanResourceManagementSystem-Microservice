package com.tolgakumbul.userservice.model.employers;

import com.tolgakumbul.userservice.model.common.CommonResponseDTO;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmployersGeneralResponseDTO implements Serializable {

    private static final long serialVersionUID = -4717846751778793485L;

    private EmployersDTO employer;
    private CommonResponseDTO commonResponse;

}
