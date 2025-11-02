package com.openclassrooms.rentalapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
@Schema(description = "DTO used to update an existing rental listing")
public class RentalUpdateDto {

    @NotBlank
    @Schema(description = "Name of the rental", example = "Cozy apartment in Paris")
    private final String name;

    @NotNull
    @Schema(description = "Surface area in square meters", example = "45.0")
    private final Double surface;

    @NotNull
    @Schema(description = "Monthly rental price in euros", example = "1200.0")
    private final Double price;

    @Size(max = 2000)
    @Schema(description = "Detailed description of the rental", example = "Bright and spacious apartment near the city center.")
    private final String description;
}

