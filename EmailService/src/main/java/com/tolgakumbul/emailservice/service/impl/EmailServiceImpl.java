package com.tolgakumbul.emailservice.service.impl;

import com.tolgakumbul.emailservice.dao.EmailActivationRepository;
import com.tolgakumbul.emailservice.model.EmailDTO;
import com.tolgakumbul.emailservice.model.confirmation.EmailConfirmationRequestDTO;
import com.tolgakumbul.emailservice.model.confirmation.EmailConfirmationResponseDTO;
import com.tolgakumbul.emailservice.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailActivationRepository emailActivationRepository;

    public EmailServiceImpl(EmailActivationRepository emailActivationRepository) {
        this.emailActivationRepository = emailActivationRepository;
    }


    @Override
    public void sendEmail(EmailDTO emailDTO) {

    }

    @Override
    public EmailConfirmationResponseDTO confirmEmail(EmailConfirmationRequestDTO emailConfirmationRequestDTO) {
        return null;
    }
}
