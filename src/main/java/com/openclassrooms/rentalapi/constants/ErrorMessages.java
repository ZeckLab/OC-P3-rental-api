package com.openclassrooms.rentalapi.constants;

public class ErrorMessages {

    public static final String EMAIL_ALREADY_IN_USE = "Email is already in use";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String USER_NOT_FOUND = "User not found with username: ";

    public static final String USER_NOT_AUTHENTICATED = "User not authenticated";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";
    public static final String UNEXPECTED_ERROR = "An unexpected error occurred";


    private ErrorMessages() {
        // Private constructor to prevent instantiation
    }
}
