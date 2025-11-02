package com.openclassrooms.rentalapi.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(description = "API Response DTO")
@Getter
@RequiredArgsConstructor
public class ApiResponseDto {
    private final String message;
    private final int status;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
