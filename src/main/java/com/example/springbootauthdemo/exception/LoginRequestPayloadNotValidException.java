package com.example.springbootauthdemo.exception;

public class LoginRequestPayloadNotValidException extends Throwable {
    public LoginRequestPayloadNotValidException(String message) {
        super(message);
    }
}
