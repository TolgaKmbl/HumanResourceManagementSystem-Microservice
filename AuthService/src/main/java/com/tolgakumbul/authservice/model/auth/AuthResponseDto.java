package com.tolgakumbul.authservice.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {

    private String token;
    private LocalDateTime tokenExpireTime;
    private String refreshToken;
    private LocalDateTime refreshTokenExpireTime;

}
