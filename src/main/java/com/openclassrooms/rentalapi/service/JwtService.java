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
     * Prend en paramètre le mail de l'utilisateur connecté
     * au lieu d'une Authentifiation car l'application ne gère
     * pas les rôles et les permissions.
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
