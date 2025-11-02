package com.openclassrooms.rentalapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Rental DTO representing a rental listing")
public record RentalDto(
        @Schema(description = "Unique identifier of the rental", example = "1") Long id,

        @Schema(description = "Name of the rental", example = "Cozy apartment in Paris") String name,

        @Schema(description = "Surface area in square meters", example = "45.0") Double surface,

        @Schema(description = "Monthly rental price in euros", example = "1200.0") Double price,

        @Schema(description = "URL or path to the rental picture", example = "/uploads/rental1.jpg") String picture,

        @Schema(description = "Detailed description of the rental", example = "Bright and spacious ...") String description,

        @Schema(description = "ID of the owner user", example = "42") Long owner_id,

        @Schema(description = "Creation timestamp", example = "2025/11/02") String created_at,

        @Schema(description = "Last update timestamp", example = "2025/11/02") String updated_at) {
}