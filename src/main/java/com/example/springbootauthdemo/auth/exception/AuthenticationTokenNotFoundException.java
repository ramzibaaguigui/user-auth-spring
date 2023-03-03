package com.example.springbootauthdemo.auth.exception;

public class AuthenticationTokenNotFoundException extends Exception {
    public AuthenticationTokenNotFoundException(String message) {
        super(message);
    }
}
