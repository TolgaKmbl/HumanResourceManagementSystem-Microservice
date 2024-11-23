package com.tolgakumbul.authservice.service.impl;

import com.tolgakumbul.authservice.entity.Authentication;
import com.tolgakumbul.authservice.entity.AuthenticationStatus;
import com.tolgakumbul.authservice.entity.Role;
import com.tolgakumbul.authservice.entity.User;
import com.tolgakumbul.authservice.exception.RefreshTokenException;
import com.tolgakumbul.authservice.exception.UserCreateException;
import com.tolgakumbul.authservice.model.auth.AuthRequestDto;
import com.tolgakumbul.authservice.model.auth.AuthResponseDto;
import com.tolgakumbul.authservice.repository.AuthenticationRepository;
import com.tolgakumbul.authservice.repository.UserRepository;
import com.tolgakumbul.authservice.service.AuthService;
import com.tolgakumbul.authservice.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationRepository authenticationRepository;

    @Override
    public String register(AuthRequestDto request) {
        try {
            User user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();

            repository.save(user);
            return "User registered succesfully";
        } catch (DataAccessException exception) {
            throw new UserCreateException("Cannot registered this user at the moment.");
        }
    }

    @Override
    public AuthResponseDto authenticate(AuthRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = repository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with this email not found: " + request.getEmail()));
        AuthResponseDto authResponse = getAuthResponse(user);

        return authResponse;
    }

    @Override
    @Transactional
    public void validateToken(String token) {
        boolean isValid = jwtService.isTokenValid(token);

        if (!isValid) {
            authenticationRepository.findByAccessToken(token)
                    .ifPresent(auth -> {
                        auth.setStatus(AuthenticationStatus.EXPIRED);
                        authenticationRepository.save(auth);
                    });
            throw new ExpiredJwtException(null, null, "Token is invalid or expired!");
        }
    }

    @Override
    @Transactional
    public AuthResponseDto refresh(String token) {

        Authentication authentication = authenticationRepository.findByRefreshTokenAndStatus(token, AuthenticationStatus.ACTIVE)
                .orElseThrow(() -> new RefreshTokenException("Refresh token is not active or could not be found!"));

        if (authentication.getRefreshExpireTime().isBefore(LocalDateTime.now())) {
            authentication.setStatus(AuthenticationStatus.EXPIRED);
            throw new RefreshTokenException("Refresh token is expired!");
        }

        User user = repository.findByEmail(authentication.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + authentication.getUsername()));

        AuthResponseDto authResponse = getAuthResponse(user);

        authentication.setStatus(AuthenticationStatus.INVALIDATED);

        return authResponse;
    }

    private AuthResponseDto getAuthResponse(User user) {
        LocalDateTime accessTokenExpireTime = LocalDateTime.now().plusHours(1);
        LocalDateTime refreshTokenExpireTime = LocalDateTime.now().plusHours(10);

        String jwtToken = jwtService.generateToken(user, accessTokenExpireTime);
        String refreshToken = jwtService.generateRefreshToken(user, refreshTokenExpireTime);


        AuthResponseDto authResponse = AuthResponseDto.builder()
                .token(jwtToken)
                .tokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();

        CompletableFuture.runAsync(() -> {
            saveTokenToDb(user, authResponse);
        });

        return authResponse;
    }

    private void saveTokenToDb(User user, AuthResponseDto authResponse) {
        Authentication authentication = new Authentication();
        authentication.setUsername(user.getEmail());
        authentication.setAccessToken(authResponse.getToken());
        authentication.setAccessExpireTime(authResponse.getTokenExpireTime());
        authentication.setRefreshExpireTime(authResponse.getRefreshTokenExpireTime());
        authentication.setRefreshToken(authResponse.getRefreshToken());
        authentication.setStatus(AuthenticationStatus.ACTIVE);
        authenticationRepository.save(authentication);
    }
}
