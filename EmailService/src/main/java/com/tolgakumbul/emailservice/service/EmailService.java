package com.tolgakumbul.emailservice.service;

import com.tolgakumbul.emailservice.model.EmailDTO;
import com.tolgakumbul.emailservice.model.confirmation.EmailConfirmationRequestDTO;
import com.tolgakumbul.emailservice.model.confirmation.EmailConfirmationResponseDTO;

public interface EmailService {

    void sendEmail(EmailDTO emailDTO);

    EmailConfirmationResponseDTO confirmEmail(EmailConfirmationRequestDTO emailConfirmationRequestDTO);

}
