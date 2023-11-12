package com.tolgakumbul.emailservice.service.impl;

import com.tolgakumbul.emailservice.dao.EmailActivationRepository;
import com.tolgakumbul.emailservice.entity.EmailActivationEntity;
import com.tolgakumbul.emailservice.mapper.EmailMapper;
import com.tolgakumbul.emailservice.model.EmailDTO;
import com.tolgakumbul.emailservice.model.confirmation.EmailConfirmationRequestDTO;
import com.tolgakumbul.emailservice.model.confirmation.EmailConfirmationResponseDTO;
import com.tolgakumbul.emailservice.service.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    public static String ACTIVATED = "Y";
    public static String NOT_ACTIVATED = "N";
    public static LocalDateTime DEFAULT_TIME = LocalDateTime.parse("1900-01-01T00:00:00.000000");

    private EmailMapper EMAIL_MAPPER = EmailMapper.INSTANCE;
    private final EmailActivationRepository emailActivationRepository;
    private final JavaMailSender emailSender;

    public EmailServiceImpl(EmailActivationRepository emailActivationRepository, JavaMailSender emailSender) {
        this.emailActivationRepository = emailActivationRepository;
        this.emailSender = emailSender;
    }


    @Override
    public void sendEmail(EmailDTO emailDTO) {

        String activationCode = "CODE";//UUID.randomUUID().toString();

        Optional<EmailActivationEntity> byUserId = emailActivationRepository.findByUserId(emailDTO.getUserId());
        if (byUserId.isPresent()) {
            /** resend mail
             */
            EmailActivationEntity entity = byUserId.get();
            entity.setActivationCode(activationCode);
            emailActivationRepository.save(entity);
        } else {
            /** send mail
             */
            EmailActivationEntity entity = EMAIL_MAPPER.toEmailActivationEntity(emailDTO, activationCode, DEFAULT_TIME);
            emailActivationRepository.save(entity);
        }

    }

    @Override
    public EmailConfirmationResponseDTO confirmEmail(EmailConfirmationRequestDTO emailConfirmationRequestDTO) {
        Optional<EmailActivationEntity> byEmail = emailActivationRepository.findByEmail(emailConfirmationRequestDTO.getEmail());
        if (byEmail.isPresent()) {
            EmailActivationEntity entity = byEmail.get();
            if (emailConfirmationRequestDTO.getActivationCode().equalsIgnoreCase(entity.getActivationCode())) {
                return new EmailConfirmationResponseDTO(emailConfirmationRequestDTO.getEmail(), ACTIVATED, LocalDateTime.now());
            }
        }
        return new EmailConfirmationResponseDTO(emailConfirmationRequestDTO.getEmail(), NOT_ACTIVATED, DEFAULT_TIME);
    }

}
