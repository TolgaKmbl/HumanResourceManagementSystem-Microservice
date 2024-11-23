package com.tolgakumbul.authservice.scheduler;

import com.tolgakumbul.authservice.repository.AuthenticationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TokenCleanupScheduler {

    private final AuthenticationRepository authenticationRepository;

    public TokenCleanupScheduler(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    /**
     * Scheduled task to delete expired tokens daily.
     * Runs every midnight.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredTokens() {
        authenticationRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}
