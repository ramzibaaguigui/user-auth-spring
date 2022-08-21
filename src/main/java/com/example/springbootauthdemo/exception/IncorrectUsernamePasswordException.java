package com.example.springbootauthdemo.exception;

public class IncorrectUsernamePasswordException extends Throwable {
    public IncorrectUsernamePasswordException(String message) {
        super(message);
    }
}
