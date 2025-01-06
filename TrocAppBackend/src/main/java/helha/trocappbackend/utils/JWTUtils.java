package helha.trocappbackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for handling JSON Web Tokens (JWT) generation, validation, and parsing.
 * This class is a Spring {@code @Component} that provides methods to generate
 * access and refresh tokens for users, validate tokens, and parse token claims.
 */
@Component
public class JWTUtils {

    /**
     * Secret key used to sign the JWT.
     * Configured via the application properties using the key {@code jwt.secret}.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Token expiration time in milliseconds for access tokens.
     * Configured via the application properties using the key {@code jwt.expirationTokenMs}.
     */
    @Value("${jwt.expirationTokenMs}")
    private long expirationToken;

    /**
     * Token expiration time in milliseconds for refresh tokens.
     * Configured via the application properties using the key {@code jwt.expirationRefreshTokenMs}.
     */
    @Value("${jwt.expirationRefreshTokenMs}")
    private long expirationRefreshToken;

    /**
     * Generates an access token for a given user.
     *
     * @param user the Spring Security {@link User} object containing authentication details.
     * @param userInternal the internal user object from the application model.
     * @return the generated JWT access token as a {@link String}.
     */
    public String generateAccessToken(User user, helha.trocappbackend.models.User userInternal) {
        return generateToken(user, userInternal, expirationToken);
    }

    /**
     * Generates a refresh token for a given user.
     *
     * @param user the Spring Security {@link User} object containing authentication details.
     * @param userInternal the internal user object from the application model.
     * @return the generated JWT refresh token as a {@link String}.
     */
    public String generateRefreshToken(User user, helha.trocappbackend.models.User userInternal) {
        return generateToken(user, userInternal, expirationRefreshToken);
    }

    /**
     * Internal method to generate a JWT token with a specified expiration time.
     *
     * @param user the Spring Security {@link User} object containing authentication details.
     * @param userInternal the internal user object from the application model.
     * @param expiration the expiration time for the token in milliseconds.
     * @return the generated JWT token as a {@link String}.
     */
    private String generateToken(User user, helha.trocappbackend.models.User userInternal, long expiration) {
        return Jwts.builder()
                .claims(Jwts.claims()
                        .subject(user.getUsername())
                        .issuedAt(new Date())
                        .expiration(new Date(new Date().getTime() + expiration))
                        .add("roles", user.getAuthorities())
                        .add("id", userInternal.getId())
                        .add("unique_name", user.getUsername())
                        .add("email", userInternal.getEmail())
                        .build())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Validates a given JWT token.
     *
     * @param token the JWT token to validate.
     * @return {@code true} if the token is valid, {@code false} otherwise.
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Parses a given JWT token and extracts its claims.
     *
     * @param token the JWT token to parse.
     * @return the {@link Claims} object containing the token's payload.
     * @throws JwtException if the token is invalid or cannot be parsed.
     */
    public Claims parseToken(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }
}

