package com.openclassrooms.rentalapi.controller;

import com.openclassrooms.rentalapi.dto.RentalCreateDto;
import com.openclassrooms.rentalapi.dto.RentalDto;
import com.openclassrooms.rentalapi.dto.RentalResponseDto;
import com.openclassrooms.rentalapi.exception.ResourceNotFoundException;
import com.openclassrooms.rentalapi.model.AppUser;
import com.openclassrooms.rentalapi.model.Rental;
import com.openclassrooms.rentalapi.repository.AppUserRepository;
import com.openclassrooms.rentalapi.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.openclassrooms.rentalapi.dto.ApiResponseDto;

import static com.openclassrooms.rentalapi.constants.ErrorMessages.*;
import static com.openclassrooms.rentalapi.constants.SuccessMessages.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/rentals")
public class RentalController {

	private final RentalService rentalService;
	private final AppUserRepository appUserRepository;

	@Operation(summary = "Create a rental", description = """
			Requires a valid JWT token.
			Creates a new rental listing for the authenticated user.
			**Note:** Although `201 CREATED` would be more appropriate for a resource creation,
			`200 OK` is returned to comply with frontend expectations.
			""")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rental created!"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT")
	})
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponseDto> createRental(
			@ModelAttribute RentalCreateDto dto,
			Authentication authentication) {

		AppUser owner = appUserRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> {
					log.error("User not found: {}", authentication.getName());
					return new UsernameNotFoundException(USER_NOT_FOUND + authentication.getName());
				});

		log.info("Creating rental for user: {}", authentication.getName());
		rentalService.createRental(dto, owner);

		return ResponseEntity.ok(
				new ApiResponseDto(RENTAL_CREATED, HttpStatus.OK.value()));
	}

	@Operation(summary = "Get all rentals", description = "Returns a list of all rental listings. Requires a valid JWT token.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of rentals returned"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT")
	})
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<RentalResponseDto> getAllRentals() {
		log.info("Fetching all rentals");
		List<RentalDto> rentals = rentalService.getAllRentals();

		log.debug("Returned {} rentals", rentals.size());

		return ResponseEntity.ok(new RentalResponseDto(rentals));
	}

	@Operation(summary = "Get rental by ID", description = "Returns a rental listing by its ID. Requires a valid JWT token.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rental returned successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT"),
			@ApiResponse(responseCode = "404", description = "Rental not found")
	})
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<RentalDto> getRentalById(@PathVariable Long id) {
		log.info("GET /rental/{} called", id);

		RentalDto rentalDto = rentalService.getRentalById(id);

		return ResponseEntity.ok(rentalDto);
	}

}
