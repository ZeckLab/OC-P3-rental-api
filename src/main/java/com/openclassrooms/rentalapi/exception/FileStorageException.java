package com.openclassrooms.rentalapi.exception;

/**
 * Custom exception thrown when an error occurs during file storage operations.
 * Typically used to wrap lower-level exceptions related to saving, reading, or
 * writing files.
 */
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
