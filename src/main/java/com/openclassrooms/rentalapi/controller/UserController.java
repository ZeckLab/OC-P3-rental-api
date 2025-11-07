package com.openclassrooms.rentalapi.controller;

import com.openclassrooms.rentalapi.dto.AppUserDto;
import com.openclassrooms.rentalapi.mapper.AppUserMapper;
import com.openclassrooms.rentalapi.model.AppUser;
import com.openclassrooms.rentalapi.repository.AppUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.openclassrooms.rentalapi.constants.ErrorMessages.*;
import com.openclassrooms.rentalapi.exception.ResourceNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    @Operation(summary = "Get user by ID", description = "Returns user details by ID. Requires a valid JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AppUserDto> getUserById(@PathVariable Long id) {
        log.info("GET /user/{} called", id);

        AppUser user = appUserRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID + id));

        AppUserDto response = appUserMapper.toDto(user);
        return ResponseEntity.ok(response);
    }

}
