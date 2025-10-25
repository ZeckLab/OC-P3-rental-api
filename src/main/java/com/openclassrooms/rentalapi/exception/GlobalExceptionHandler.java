package com.openclassrooms.rentalapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

import com.openclassrooms.rentalapi.constants.ErrorMessages;
import com.openclassrooms.rentalapi.dto.ApiResponseDto;

// TO DO : Vérifier si cette classe est toujours utile avec la nouvelle gestion des erreurs, car le front attend juste un status 401 sans message détaillé
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleUserNotFound(UsernameNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ApiResponseDto(ErrorMessages.USER_NOT_AUTHENTICATED, HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleGenericException(Exception ex) {
        log.error("Unhandled exception caught", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponseDto(ErrorMessages.UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}

