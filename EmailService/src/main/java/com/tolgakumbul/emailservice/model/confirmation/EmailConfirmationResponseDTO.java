package com.tolgakumbul.emailservice.model.confirmation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfirmationResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3284352986897952641L;

    private String email;
    private String isActivated;
    private LocalDateTime activationDate;
}
