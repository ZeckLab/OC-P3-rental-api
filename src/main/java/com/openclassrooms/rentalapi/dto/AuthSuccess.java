package com.openclassrooms.rentalapi.dto;

public class AuthSuccess {
    private String token;

    public AuthSuccess(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
