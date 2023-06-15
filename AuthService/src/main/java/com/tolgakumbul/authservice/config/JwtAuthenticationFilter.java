package com.tolgakumbul.authservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tolgakumbul.authservice.core.ErrorResult;
import com.tolgakumbul.authservice.service.impl.JwtServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtServiceImpl jwtServiceImpl;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {


        try {
            String jwt = resolveToken(request);
            if (StringUtils.hasText(jwt)) {
                if (jwtServiceImpl.isTokenValid(jwt)) {
                    Authentication authentication = jwtServiceImpl.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);

            resetAuthenticationAfterRequest();
        } catch (ExpiredJwtException eje) {
            LOGGER.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().print(new ObjectMapper().writeValueAsString(new ErrorResult(eje.getMessage())));
            response.flushBuffer();
            LOGGER.debug("Exception " + eje.getMessage(), eje);
        }
    }


    private void resetAuthenticationAfterRequest() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(SecurityConfig.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConfig.BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
