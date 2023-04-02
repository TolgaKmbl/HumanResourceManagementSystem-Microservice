package com.tolgakumbul.authservice.controller;

import com.tolgakumbul.authservice.model.auth.AuthRequest;
import com.tolgakumbul.authservice.model.auth.AuthResponse;
import com.tolgakumbul.authservice.model.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth/v0")
public interface AuthController {

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request);

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request);

}
