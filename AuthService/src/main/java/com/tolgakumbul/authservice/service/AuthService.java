package com.tolgakumbul.authservice.service;

import com.tolgakumbul.authservice.model.auth.AuthRequestDto;
import com.tolgakumbul.authservice.model.auth.AuthResponseDto;

public interface AuthService {

    AuthResponseDto register(AuthRequestDto request);

    AuthResponseDto authenticate(AuthRequestDto request);

}
