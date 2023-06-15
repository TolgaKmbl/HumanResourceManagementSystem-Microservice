package com.tolgakumbul.authservice.controller.impl;

import com.tolgakumbul.authservice.controller.AuthController;
import com.tolgakumbul.authservice.model.auth.AuthRequestDto;
import com.tolgakumbul.authservice.model.auth.AuthResponseDto;
import com.tolgakumbul.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<String> register(AuthRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Override
    public ResponseEntity<AuthResponseDto> authenticate(AuthRequestDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @Override
    public ResponseEntity<Boolean> validateToken(String token) {
        authService.validateToken(token);
        return ResponseEntity.ok(true);
    }
}
