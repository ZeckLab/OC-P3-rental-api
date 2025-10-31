package com.openclassrooms.rentalapi.controller;

import com.openclassrooms.rentalapi.dto.RentalCreateDto;
import com.openclassrooms.rentalapi.model.AppUser;
import com.openclassrooms.rentalapi.repository.AppUserRepository;
import com.openclassrooms.rentalapi.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.openclassrooms.rentalapi.dto.ApiResponseDto;

import static com.openclassrooms.rentalapi.constants.ErrorMessages.*;
import static com.openclassrooms.rentalapi.constants.SuccessMessages.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rentals")
public class RentalController {

        private final RentalService rentalService;
        private final AppUserRepository appUserRepository;

        @Operation(summary = "Create a rental", description = "Creates a new rental listing for the authenticated user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Rental created!"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT")
        })
        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<?> createRental(@ModelAttribute RentalCreateDto dto, Authentication authentication) {
                AppUser owner = appUserRepository.findByEmail(authentication.getName())
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                USER_NOT_FOUND + authentication.getName()));

                rentalService.createRental(dto, owner);
                return ResponseEntity.ok(
                                new ApiResponseDto(RENTAL_CREATED, HttpStatus.OK.value()));
        }
}
