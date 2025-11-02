package com.openclassrooms.rentalapi.constants;

public class ErrorMessages {

    // User-related error messages
    public static final String EMAIL_ALREADY_IN_USE = "Email is already in use";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String USER_NOT_FOUND = "User not found with username: ";

    // Authentication-related error messages
    public static final String USER_NOT_AUTHENTICATED = "User not authenticated";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";
    public static final String UNEXPECTED_ERROR = "An unexpected error occurred";

    // Rental-related error messages
    public static final String RENTAL_NOT_FOUND = "Rental not found with ID: ";
    public static final String INVALID_PICTURE = "Picture is required and must not be empty.";
    public static final String FILE_STORAGE_FAILED = "Failed to store file: ";
    public static final String INVALID_FORM_DATA = "Invalid form data received.";
    public static final String JWT_EXTRACTION_FAILED = "Failed to extract user ID from token.";

    private ErrorMessages() {
        // Private constructor to prevent instantiation
    }
}
