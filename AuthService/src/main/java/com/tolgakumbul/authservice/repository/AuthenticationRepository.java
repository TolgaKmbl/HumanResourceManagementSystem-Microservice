package com.tolgakumbul.authservice.repository;

import com.tolgakumbul.authservice.entity.Authentication;
import com.tolgakumbul.authservice.entity.AuthenticationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

    Optional<Authentication> findByRefreshToken(String token);

    Optional<Authentication> findByRefreshTokenAndStatus(String refreshToken, AuthenticationStatus status);

    Optional<Authentication> findByAccessToken(String accessToken);

    @Transactional
    @Modifying
    @Query("DELETE FROM Authentication a WHERE a.refreshExpireTime < :expireTime")
    void deleteExpiredTokens(@Param("expireTime") LocalDateTime expireTime);



}
