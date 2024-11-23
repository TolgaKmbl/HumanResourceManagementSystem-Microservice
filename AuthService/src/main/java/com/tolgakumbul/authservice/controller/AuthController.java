package com.tolgakumbul.authservice.controller;

import com.tolgakumbul.authservice.core.Result;
import com.tolgakumbul.authservice.model.auth.AuthRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth/v0")
public interface AuthController {

    @PostMapping("/register")
    ResponseEntity<Result> register(@RequestBody AuthRequestDto request);

    @PostMapping("/authenticate")
    ResponseEntity<Result> authenticate(@RequestBody AuthRequestDto request);

    @GetMapping("/validate")
    ResponseEntity<Result> validateToken(@RequestHeader("token") String token);

    @GetMapping("/refresh")
    ResponseEntity<Result> refreshToken(@RequestHeader("token") String token);

}
