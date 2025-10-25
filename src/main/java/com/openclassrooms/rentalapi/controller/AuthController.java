package com.openclassrooms.rentalapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

//import com.openclassrooms.rentalapi.constants.ErrorMessages;
import com.openclassrooms.rentalapi.constants.SuccessMessages;
import com.openclassrooms.rentalapi.dto.ApiResponseDto;
import com.openclassrooms.rentalapi.dto.AuthSuccess;
import com.openclassrooms.rentalapi.dto.LoginRequestDto;
import com.openclassrooms.rentalapi.dto.RegisterRequestDto;
import com.openclassrooms.rentalapi.dto.AppUserDto;
import com.openclassrooms.rentalapi.repository.AppUserRepository;
import com.openclassrooms.rentalapi.service.JwtService;
import static com.openclassrooms.rentalapi.constants.ErrorMessages.*;
import com.openclassrooms.rentalapi.model.AppUser;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager AuthenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = AuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()));
            String token = jwtService.generateToken(authentication);
            return ResponseEntity.ok(new AuthSuccess(token));
        } catch (BadCredentialsException bce) {
            // TO DO : ancienne gestion d'erreur avec message détaillé
            // A modifier pour correspondre au Front qui attend juste un status 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponseDto(INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED.value()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto request) {
        if (appUserRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{}");
            // ancienne gestion d'erreur
            /*return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponseDto(ErrorMessages.EMAIL_ALREADY_IN_USE, HttpStatus.CONFLICT.value()));*/
        }

        AppUser user = AppUser.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        appUserRepository.save(user);

        /* TO DO : nouvelle gestion avec token à la création d'un utilisateur
        la méthode generateToken attend un objet Authentication, donc soit j'authentifie l'utilisateur fraîchement créé,
        soit je modifie la méthode pour qu'elle puisse accepter un AppUser directement.

        String token = jwtService.generateToken(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthSuccess(token));
        */

        // TO DO : ancienne gestion de la réponse sans token à la création d'un utilisateur
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto(SuccessMessages.USER_REGISTERED, HttpStatus.CREATED.value()));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();

        log.info("Authenticated user retrieved: {}", email);

        AppUser user = appUserRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + email));

        log.info("Authenticated user retrieved");

        AppUserDto dto = new AppUserDto(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );

        log.debug("Returning user DTO: {}", dto);

        return ResponseEntity.ok(dto);
    }

}
