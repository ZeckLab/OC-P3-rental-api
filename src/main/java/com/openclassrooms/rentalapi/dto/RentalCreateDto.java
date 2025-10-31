package com.openclassrooms.rentalapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class RentalCreateDto {

    @NotBlank
    private final String name;

    @NotNull
    private final Double surface;

    @NotNull
    private final Double price;

    private final MultipartFile picture;

    @Size(max = 2000)
    private final String description;
}
