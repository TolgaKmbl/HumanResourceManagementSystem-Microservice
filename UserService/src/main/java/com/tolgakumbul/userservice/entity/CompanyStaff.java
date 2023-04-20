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
@Table(name = "COMPANY_STAFF")
public class CompanyStaff {

    @Column("USER_ID")
    private Long userId;
    @Column("FIRST_NAME")
    private String firstName;
    @Column("LAST_NAME")
    private String lastName;
    @Column("STATUS")
    private String status;
    @Column("APPROVAL_DATE")
    private LocalDateTime approvalDate;
}
