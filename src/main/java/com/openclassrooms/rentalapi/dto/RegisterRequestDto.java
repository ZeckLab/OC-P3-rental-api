package com.openclassrooms.rentalapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "Register request payload")
public class RegisterRequestDto {

    @Email
    @NotBlank
    @Schema(description = "User email", example = "test@test.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Schema(description = "User password", example = "test!31", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "User name", example = "Stéphanie", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String name;
}
