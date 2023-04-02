package com.tolgakumbul.authservice.controller.impl;

import com.tolgakumbul.authservice.controller.AuthController;
import com.tolgakumbul.authservice.model.auth.AuthRequest;
import com.tolgakumbul.authservice.model.auth.AuthResponse;
import com.tolgakumbul.authservice.model.auth.RegisterRequest;
import com.tolgakumbul.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Override
    public ResponseEntity<AuthResponse> authenticate(AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
