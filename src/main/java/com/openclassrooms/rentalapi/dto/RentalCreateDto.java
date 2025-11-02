package com.openclassrooms.rentalapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@AllArgsConstructor
@Schema(description = "DTO used to create a new rental listing")
public class RentalCreateDto {

    @NotBlank
    @Schema(description = "Name of the rental", example = "Cozy apartment in Paris", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String name;

    @NotNull
    @Schema(description = "Surface area in square meters", example = "45.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Double surface;

    @NotNull
    @Schema(description = "Monthly rental price in euros", example = "1200.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private final Double price;

    @Schema(description = "Picture of the rental property (optional)")
    private final MultipartFile picture;

    @Size(max = 2000)
    @Schema(description = "Detailed description of the rental", example = "Bright and spacious apartment near the city center.")
    private final String description;
}
