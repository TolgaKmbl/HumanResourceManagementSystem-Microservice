package com.tolgakumbul.authservice.service.impl;

import com.tolgakumbul.authservice.entity.Role;
import com.tolgakumbul.authservice.entity.User;
import com.tolgakumbul.authservice.model.auth.AuthRequestDto;
import com.tolgakumbul.authservice.model.auth.AuthResponseDto;
import com.tolgakumbul.authservice.repository.UserRepository;
import com.tolgakumbul.authservice.service.AuthService;
import com.tolgakumbul.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto register(AuthRequestDto request) {

        User user = User.builder()
                /*.firstName(request.getFirstName())
                .lastName(request.getLastName())*/
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);

        return getAuthResponse(user);
    }

    @Override
    public AuthResponseDto authenticate(AuthRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = repository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with this email not found: " + request.getEmail()));
        return getAuthResponse(user);
    }


    private AuthResponseDto getAuthResponse(User user) {
        String jwtToken = jwtService.generateToken(user);

        return AuthResponseDto.builder().token(jwtToken).build();
    }
}
