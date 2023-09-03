package com.tolgakumbul.emailservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "EMAIL_ACTIVATION")
public class EmailActivationEntity extends BaseDaoEntity {

    @Id
    @GeneratedValue(generator = "email_seq")
    @SequenceGenerator(name = "email_seq", sequenceName="SEQ_EMAIL_TABLE", allocationSize=1)
    private Integer id;
    @Column(name = "USER_ID")
    private Integer userId;
    @Column(name = "ACTIVATION_CODE")
    private String activationCode;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "IS_ACTIVATED")
    private String isActivated;
    @Column(name = "ACTIVATION_DATE")
    private LocalDateTime activationDate;

}
