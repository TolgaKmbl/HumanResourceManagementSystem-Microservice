package com.tolgakumbul.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EMPLOYERS")
public class EmployersEntity {

    @Column("USER_ID")
    private Long userId;
    @Column("COMPANY_NAME")
    private String companyName;
    @Column("WEBSITE")
    private String website;
    @Column("PHONE_NUM")
    private String phoneNum;
    @Column("COMPANY_IMG_URL")
    private String companyImgUrl;
}
