package com.footystars.security;

import com.footystars.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

/**
 * Service responsible for handling JWT token generation, validation, and extraction of claims.
 */
@Component
public class JwtService {

    @Value("${JWT_SECRET}")
    private String jwtSecretKey;

    /**
     * Retrieves the signing key for JWT token verification and generation.
     *
     * @return a {@link Key} used for signing and verifying JWT tokens.
     */
    @NotNull
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }

    /**
     * Extracts the username (subject) from a given JWT token.
     *
     * @param token the JWT token from which the username is extracted.
     * @return the username (email) contained in the token.
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extracts all claims from a given JWT token.
     *
     * @param token the JWT token to be parsed.
     * @return the {@link Claims} extracted from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if a given JWT token has expired.
     *
     * @param token the JWT token to be checked.
     * @return {@code true} if the token is expired, otherwise {@code false}.
     */
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /**
     * Generates a new JWT token for a given username.
     *
     * @param userName the username (email) for which the token is generated.
     * @return a JWT token valid for 5 hours.
     */
    public String generateToken(String userName) {
        return Jwts.builder()
                .setClaims(new HashMap<>()) // Empty claims
                .setSubject(userName)
                .setIssuedAt(new Date()) // Current time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 300)) // Expiration set to 5 hours
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a JWT token against a given user.
     * The token is considered valid if the extracted username matches the user's email and is not expired.
     *
     * @param token the JWT token to validate.
     * @param user  the {@link User} to verify against.
     * @return {@code true} if the token is valid, otherwise {@code false}.
     */
    public boolean isTokenValid(String token, @NotNull User user) {
        String extractedUsername = extractUsername(token);
        return user.getEmail().equals(extractedUsername) && !isTokenExpired(token);
    }

    /**
     * Validates a JWT token.
     * Ensures that the token contains valid claims and has not expired.
     *
     * @param token the JWT token to validate.
     * @return {@code true} if the token is valid, otherwise {@code false}.
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims != null && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
