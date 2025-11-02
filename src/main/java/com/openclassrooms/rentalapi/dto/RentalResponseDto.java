package com.openclassrooms.rentalapi.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response containing a list of rentals")
public record RentalResponseDto(

        @Schema(description = "List of rental items") List<RentalDto> rentals) {
}
