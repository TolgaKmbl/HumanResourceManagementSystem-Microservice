package com.tolgakumbul.authservice.service;

import com.tolgakumbul.authservice.model.auth.AuthRequestDto;
import com.tolgakumbul.authservice.model.auth.AuthResponseDto;

public interface AuthService {

    String register(AuthRequestDto request);

    AuthResponseDto authenticate(AuthRequestDto request);

}
