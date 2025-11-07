package com.openclassrooms.rentalapi.controller;

import com.openclassrooms.rentalapi.dto.ApiResponseDto;
import com.openclassrooms.rentalapi.dto.MessageRequestDto;
import com.openclassrooms.rentalapi.model.AppUser;
import com.openclassrooms.rentalapi.service.MessageService;
import com.openclassrooms.rentalapi.repository.AppUserRepository;
import static com.openclassrooms.rentalapi.constants.ErrorMessages.USER_NOT_FOUND;
import static com.openclassrooms.rentalapi.constants.SuccessMessages.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final AppUserRepository appUserRepository;

    @Operation(summary = "Send a message", description = """
        Requires a valid JWT token.
        Sends a message from the authenticated user to a rental owner for a rental.
        Returns 200 OK to match frontend expectations, even though 201 would be more RESTful.
        """)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Message sent successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid JWT"),
        @ApiResponse(responseCode = "404", description = "User or rental not found"),
        @ApiResponse(responseCode = "403", description = "Access denied – identity mismatch")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto> createMessage(@RequestBody @Valid MessageRequestDto dto, Authentication authentication) {

        AppUser sender = appUserRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> {
					log.error("User not found: {}", authentication.getName());
					return new UsernameNotFoundException(USER_NOT_FOUND + authentication.getName());
				});

        log.info("User {} is sending a message to rental {}", sender.getId(), dto.getRentalId());

        messageService.createMessage(dto, sender);

        return ResponseEntity.ok(new ApiResponseDto(MESSAGE_SENT, HttpStatus.OK.value()));

    }
}

