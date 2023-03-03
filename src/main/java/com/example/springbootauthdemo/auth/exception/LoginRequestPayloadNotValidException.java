package com.example.springbootauthdemo.auth.exception;

public class LoginRequestPayloadNotValidException extends Throwable {
    public LoginRequestPayloadNotValidException(String message) {
        super(message);
    }
}
