package com.tolgakumbul.userservice.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class SecurityContextHelper {

    private static String ISSUER;
    private static String SECRET;


    public static boolean isJwtTokenValid(String token) {
        try {
            Jws<Claims> jws = getClaimsJws(token);
            Instant now = Instant.now();
            Date expiration = jws.getBody().getExpiration();
            boolean isNotExpired = expiration != null && expiration.toInstant().isAfter(now);
            if (isNotExpired) {
                setSecurityContext(jws);
            }
            return isNotExpired;
        } catch (Exception e) {
            return false;
        }
    }

    public static void resetSecurityContext() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private static void setSecurityContext(Jws<Claims> jws) {
        Claims claims = jws.getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("role").toString().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, "", authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private static Jws<Claims> getClaimsJws(String token) {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .requireIssuer(ISSUER)
                .build()
                .parseClaimsJws(token);
        return jws;
    }

    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Value("${jwt.custom.issuer}")
    public void setIssuer(String issuer) {
        ISSUER = issuer;
    }

    @Value("${jwt.custom.secret}")
    public void setSecret(String secret) {
        SECRET = secret;
    }

}

