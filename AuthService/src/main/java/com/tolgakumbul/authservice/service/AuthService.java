package com.tolgakumbul.authservice.service;

import com.tolgakumbul.authservice.entity.Authentication;
import com.tolgakumbul.authservice.model.auth.AuthRequestDto;
import com.tolgakumbul.authservice.model.auth.AuthResponseDto;

import java.util.List;

public interface AuthService {

    String register(AuthRequestDto request);

    AuthResponseDto authenticate(AuthRequestDto request);

    void validateToken(String token);

    AuthResponseDto refresh(String token);

}
