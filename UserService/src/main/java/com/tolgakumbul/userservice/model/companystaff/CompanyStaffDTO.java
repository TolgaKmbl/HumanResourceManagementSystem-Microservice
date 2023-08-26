package com.tolgakumbul.userservice.model.companystaff;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CompanyStaffDTO implements Serializable {

    private static final long serialVersionUID = 7792090223494747414L;

    private Long userId;
    private String firstName;
    private String lastName;
    private IsApprovedEnum isApproved;
    private LocalDateTime approvalDate;
    private String isDeleted;
}
