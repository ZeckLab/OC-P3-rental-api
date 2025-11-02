package com.openclassrooms.rentalapi.service;

import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import java.time.Instant;
import java.util.List;

@Service
public class JwtService {

    private JwtEncoder jwtEncoder;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    /**
     * Generates a JWT token using the user's email as the subject.
     * <p>
     * This method does not rely on a full Authentication object,
     * as the application does not implement role or permission management.
     */
    public String generateToken(String username) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(jwtExpirationMs);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("chatop")
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(username)
                .claim("roles", List.of("ROLE_USER"))
                .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return this.jwtEncoder.encode(parameters).getTokenValue();
    }
}
