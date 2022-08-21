package com.example.springbootauthdemo.exception;

public class AuthenticationTokenNotFoundException extends Exception{
    public AuthenticationTokenNotFoundException(String message) {
        super(message);
    }
}
