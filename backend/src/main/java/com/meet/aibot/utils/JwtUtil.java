package com.meet.aibot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String secretKey;
    // Token validity period: 24 hours
    private final long jwtExpirationInMs = 24 * 60 * 60 * 1000;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    // Generate a signing key using the secretKey
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Generate a token with custom claims (email and id) and set the subject as email
    public String generateToken(String email, String id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("id", id);
        return createToken(claims, email);
    }

    // Build the token using claims, subject, issue date, expiration date, and sign it
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract the username (subject) from the token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // Extract all claims present in the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate token: check username (subject) and token expiration
    public boolean validateToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return tokenUsername.equals(username) && !isTokenExpired(token);
    }
}
