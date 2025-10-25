package com.openclassrooms.rentalapi.service;

import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

import org.springframework.security.core.Authentication;
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

    /* TO DO : 
     * Modifier si nécessaire le paramètre de la méthode Authentication authentication
     * par un AppUser appUser ou autre. Et voir si AppUser doit implémeter UserDetails
    */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(jwtExpirationMs);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("chatop")
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(authentication.getName())
                .claim("roles", List.of("ROLE_USER"))
                .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return this.jwtEncoder.encode(parameters).getTokenValue();
    }
}
