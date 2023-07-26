package com.tolgakumbul.userservice.model.employers;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmployersListResponseDTO implements Serializable {

    private static final long serialVersionUID = 7748117111067425266L;

    private List<EmployersDTO> employerList;
    //private CommonResponseDTO commonResponse;
}
