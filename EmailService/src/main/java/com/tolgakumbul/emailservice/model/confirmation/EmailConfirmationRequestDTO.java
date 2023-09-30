package com.tolgakumbul.emailservice.model.confirmation;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class EmailConfirmationRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1442425481935659318L;

    private String activationCode;
    private String email;
}
