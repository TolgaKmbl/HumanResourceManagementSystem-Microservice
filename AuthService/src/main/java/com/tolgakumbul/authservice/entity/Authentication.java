package com.tolgakumbul.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth", indexes = {
        @Index(name = "idx_username", columnList = "username"),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_auth_access_token", columnList = "accessToken")
})
public class Authentication {

    @Id
    @GeneratedValue
    private Long id;


    private String username;
    @Column(length = 4000)
    private String accessToken;
    private LocalDateTime accessExpireTime;
    @Column(length = 4000)
    private String refreshToken;
    private LocalDateTime refreshExpireTime;
    @Enumerated(EnumType.STRING)
    private AuthenticationStatus status;
}
