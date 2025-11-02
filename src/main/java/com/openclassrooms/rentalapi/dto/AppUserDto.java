package com.openclassrooms.rentalapi.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Authenticated user profile")
public class AppUserDto {
    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User email", example = "test@test.com")
    private String email;

    @Schema(description = "User name", example = "Stéphanie")
    private String name;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @JsonProperty("created_at")
    @Schema(description = "Account creation date", example = "2023/01/01")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @JsonProperty("updated_at")
    @Schema(description = "Last update date", example = "2023/10/01")
    private LocalDateTime updatedAt;
}
