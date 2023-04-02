package com.tolgakumbul.authservice.service.impl;

import com.tolgakumbul.authservice.model.Role;
import com.tolgakumbul.authservice.model.User;
import com.tolgakumbul.authservice.model.auth.AuthRequest;
import com.tolgakumbul.authservice.model.auth.AuthResponse;
import com.tolgakumbul.authservice.model.auth.RegisterRequest;
import com.tolgakumbul.authservice.repository.UserRepository;
import com.tolgakumbul.authservice.service.AuthService;
import com.tolgakumbul.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);

        return getAuthResponse(user);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = repository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with this email not found: " + request.getEmail()));
        return getAuthResponse(user);
    }


    private AuthResponse getAuthResponse(User user) {
        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder().token(jwtToken).build();
    }
}
