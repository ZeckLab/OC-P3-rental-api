package com.openclassrooms.rentalapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "Message request")
public class MessageRequestDto {

    @NotNull
    @JsonProperty("rental_id")
    @Schema(description = "ID of the rental associated with the message", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long rentalId;

    @NotNull
    @JsonProperty("user_id")
    @Schema(description = "ID of the user sending the message", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @NotBlank
    @Size(max = 2000)
    @Schema(description = "Content of the message", example = "Is this rental still available?", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

}
