package com.tolgakumbul.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JOB_SEEKERS")
public class JobSeekersEntity {

    @Column("USER_ID")
    private Long userId;
    @Column("FIRST_NAME")
    private String firstName;
    @Column("LAST_NAME")
    private String lastName;
    @Column("NATIONAL_ID")
    private String nationalId;
    @Column("BIRTH_DATE")
    private LocalDateTime birthDate;
    @Column("CV_ID")
    private Long cvId;
    @Column("IS_APPROVED")
    private String isApproved;
    @Column("APPROVAL_DATE")
    private LocalDateTime approvalDate;

}
