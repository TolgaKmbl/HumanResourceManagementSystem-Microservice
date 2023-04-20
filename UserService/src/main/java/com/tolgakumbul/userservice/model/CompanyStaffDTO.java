package com.tolgakumbul.userservice.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CompanyStaffDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String isApproved;
    private LocalDateTime approvalDate;

}
