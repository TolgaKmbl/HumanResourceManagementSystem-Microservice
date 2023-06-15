package com.tolgakumbul.authservice.controller.impl;

import com.tolgakumbul.authservice.controller.AuthController;
import com.tolgakumbul.authservice.core.Result;
import com.tolgakumbul.authservice.core.SuccessDataResult;
import com.tolgakumbul.authservice.core.SuccessResult;
import com.tolgakumbul.authservice.model.auth.AuthRequestDto;
import com.tolgakumbul.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<Result> register(AuthRequestDto request) {
        return ResponseEntity.ok(new SuccessResult(authService.register(request)));
    }

    @Override
    public ResponseEntity<Result> authenticate(AuthRequestDto request) {
        return ResponseEntity.ok(new SuccessDataResult<>(authService.authenticate(request), "Success"));
    }

    @Override
    public ResponseEntity<Result> validateToken(String token) {
        authService.validateToken(token);
        return ResponseEntity.ok(new SuccessResult("Token is valid"));
    }
}
