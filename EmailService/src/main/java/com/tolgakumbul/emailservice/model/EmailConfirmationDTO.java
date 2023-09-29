package com.tolgakumbul.emailservice.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EmailConfirmationDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1442425481935659318L;

    private String activationCode;
    private String email;
    private String status;
}
