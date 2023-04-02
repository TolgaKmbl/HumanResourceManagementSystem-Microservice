package com.tolgakumbul.authservice.service;

import com.tolgakumbul.authservice.model.auth.AuthRequest;
import com.tolgakumbul.authservice.model.auth.AuthResponse;
import com.tolgakumbul.authservice.model.auth.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse authenticate(AuthRequest request);

}
