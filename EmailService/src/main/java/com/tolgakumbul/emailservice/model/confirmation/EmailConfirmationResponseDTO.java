package com.tolgakumbul.emailservice.model.confirmation;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EmailConfirmationResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3284352986897952641L;

    private String email;
    private String isActivated;
    private LocalDateTime activationDate;
}
