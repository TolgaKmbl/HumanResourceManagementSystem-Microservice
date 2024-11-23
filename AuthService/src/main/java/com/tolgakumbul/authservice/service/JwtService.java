package com.tolgakumbul.authservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails, LocalDateTime expireDateTime);

    String generateRefreshToken(UserDetails userDetails, LocalDateTime expireDateTime);

    Boolean isTokenValid(String token);

    Authentication getAuthentication(String token);
}
