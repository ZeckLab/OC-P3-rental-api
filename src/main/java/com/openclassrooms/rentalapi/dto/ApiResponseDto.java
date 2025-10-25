package com.openclassrooms.rentalapi.dto;

import java.time.LocalDateTime;

public class ApiResponseDto {
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ApiResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
