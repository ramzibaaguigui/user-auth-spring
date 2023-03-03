package com.example.springbootauthdemo.auth.exception;

public class IncorrectUsernamePasswordException extends Throwable {
    public IncorrectUsernamePasswordException(String message) {
        super(message);
    }
}
