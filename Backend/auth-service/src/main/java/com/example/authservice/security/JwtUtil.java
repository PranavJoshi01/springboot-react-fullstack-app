package com.example.authservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key used to sign JWT
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generate JWT Token
    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setSubject(username)               // who the user is
                .claim("role", role)                // custom data
                .setIssuedAt(new Date())             // token creation time
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60)
                )                                    // 1 hour expiry
                .signWith(key)                       // digital signature
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {
        return parseToken(token)
                .getBody()
                .getSubject();
    }

    // Validate token (check signature + expiry)
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Internal method to parse token
    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
