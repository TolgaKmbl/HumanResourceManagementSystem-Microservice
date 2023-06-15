package com.tolgakumbul.authservice.controller;

import com.tolgakumbul.authservice.model.auth.AuthRequestDto;
import com.tolgakumbul.authservice.model.auth.AuthResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth/v0")
public interface AuthController {

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody AuthRequestDto request);

    @PostMapping("/authenticate")
    ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto request);

    @GetMapping("/validate")
    ResponseEntity<Boolean> validateToken(@RequestHeader("token") String token);

}
