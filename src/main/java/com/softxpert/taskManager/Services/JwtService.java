package com.softxpert.taskManager.Services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.softxpert.taskManager.Entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET = "YjE5MDU3MzhlZjE0NWQ2NDdiODg5YTE1OGRjYmRjNjIxYTk4NzlkYjE0ODNkNzA1";
    
    /**
     * Generates and returns the signing key used for JWT token creation.
     *
     * @return the HMAC SHA signing key
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a JWT token for the given user.
     *
     * @param user the authenticated user for whom the token is generated
     * @return a signed JWT token containing user details
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username (subject) from the provided JWT token.
     *
     * @param token the JWT token
     * @return the username contained in the token
     */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extracts the user's role from the JWT token.
     *
     * @param token the JWT token
     * @return the role claim as an Integer
     */
    public Integer extractRole(String token) {
        return getClaims(token).get("role", Integer.class);
    }

    /**
     * Extracts the user ID from the JWT token.
     *
     * @param token the JWT token
     * @return the userId claim as a Long
     */
    public Long extractUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    /**
     * Validates the JWT token against the provided user details.
     *
     * @param token the JWT token
     * @param userDetails the user details to validate against
     * @return true if valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isExpired(token);
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token the JWT token
     * @return true if expired, false otherwise
     */
    private boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
    
    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the token's claims
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
