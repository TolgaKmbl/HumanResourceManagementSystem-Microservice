package com.tolgakumbul.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EMPLOYERS")
public class EmployersEntity extends BaseDaoEntity implements Serializable {

    private static final long serialVersionUID = 4516888019581455554L;

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
