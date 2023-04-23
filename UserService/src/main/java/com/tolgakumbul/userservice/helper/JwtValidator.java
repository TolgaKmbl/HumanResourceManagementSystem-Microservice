package com.tolgakumbul.userservice.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

public class JwtValidator {

    private static final String ISSUER = "HumanResourceManagementSystemApp";
    private static final String SECRET = "576D5A7134743777217A24432646294A404E635266556A586E3272357538782F";
    /*private static final Key KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);*/

    public static boolean isValid(String token) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .requireIssuer(ISSUER)
                    .build()
                    .parseClaimsJws(token);
            Instant now = Instant.now();
            Date expiration = jws.getBody().getExpiration();
            return expiration != null && expiration.toInstant().isAfter(now);
        } catch (Exception e) {
            return false;
        }
    }
    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


   /* public static String generateToken(String userId) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(3600);
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(userId)
                .setExpiration(Date.from(expiration))
                .signWith(KEY)
                .compact();
    }*/
}

