package com.openclassrooms.rentalapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

import com.openclassrooms.rentalapi.dto.AuthSuccess;
import com.openclassrooms.rentalapi.dto.LoginRequestDto;
import com.openclassrooms.rentalapi.dto.RegisterRequestDto;
import com.openclassrooms.rentalapi.mapper.AppUserMapper;
import com.openclassrooms.rentalapi.dto.AppUserDto;
import com.openclassrooms.rentalapi.repository.AppUserRepository;
import com.openclassrooms.rentalapi.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import static com.openclassrooms.rentalapi.constants.ErrorMessages.*;
import com.openclassrooms.rentalapi.model.AppUser;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserMapper appUserMapper;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            AppUserMapper appUserMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserMapper = appUserMapper;
    }

    @Operation(summary = "Login", description = "Authenticates a user and returns a JWT token if credentials are valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT returned"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthSuccess> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("Login attempt for email: {}", loginRequestDto.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()));
            String token = jwtService.generateToken(authentication.getName());
            log.info("Login successful for user: {}", authentication.getName());

            return ResponseEntity.ok(new AuthSuccess(token));
        } catch (BadCredentialsException bce) {
            log.warn("Login failed — invalid credentials for email: {}", loginRequestDto.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @Operation(summary = "Register", description = "Registers a new user and returns a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict - email already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthSuccess> register(@Valid @RequestBody RegisterRequestDto request) {
        log.info("Registration attempt for email: {}", request.getEmail());

        if (appUserRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("Registration failed — email already exists: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        AppUser user = AppUser.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        appUserRepository.save(user);
        log.info("New user registered: {}", user.getEmail());

        String token = jwtService.generateToken(user.getEmail());
        log.debug("JWT token generated for user: {}", user.getEmail());

        return ResponseEntity.ok(new AuthSuccess(token));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get current authenticated user", description = "Returns the profile information of the currently authenticated user. Requires a valid JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile returned"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT")
    })
    @GetMapping("/me")
    public ResponseEntity<AppUserDto> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        log.info("Authenticated user retrieved: {}", email);

        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + email));
        log.info("Authenticated user retrieved");

        AppUserDto dto = appUserMapper.toDto(user);
        log.debug("Returning user DTO: {}", dto);

        return ResponseEntity.ok(dto);
    }

}
