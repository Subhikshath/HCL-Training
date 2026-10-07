package CanteenBooking.com.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /*
     * Creates the secret key used for signing and validating JWT.
     */
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }


    /*
     * Generates JWT token.
     *
     * Email is stored as the subject.
     * Role is stored as a claim.
     */
    public String generateToken(
            String email,
            String role
    ) {

        Date issuedAt = new Date();

        Date expirationDate =
                new Date(issuedAt.getTime() + expiration);

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(issuedAt)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }


    /*
     * Extracts email from JWT.
     */
    public String extractEmail(String token) {

        return getClaims(token)
                .getSubject();
    }


    /*
     * Extracts role from JWT.
     */
    public String extractRole(String token) {

        return getClaims(token)
                .get("role", String.class);
    }


    /*
     * Validates the JWT.
     */
    public boolean isTokenValid(
            String token,
            String email
    ) {

        try {

            String tokenEmail = extractEmail(token);

            return tokenEmail.equals(email)
                    && !isTokenExpired(token);

        } catch (Exception e) {

            return false;
        }
    }


    /*
     * Checks whether JWT has expired.
     */
    private boolean isTokenExpired(String token) {

        Date expirationDate =
                getClaims(token)
                        .getExpiration();

        return expirationDate.before(new Date());
    }


    /*
     * Extracts all claims from JWT.
     */
    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}