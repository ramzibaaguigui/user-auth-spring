package com.example.springbootauthdemo.auth.exception;

public class UserInfoEmptyException extends Exception {
    public UserInfoEmptyException(String message) {
        super(message);
    }
}
