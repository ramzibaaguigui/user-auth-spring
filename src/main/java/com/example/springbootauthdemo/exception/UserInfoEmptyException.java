package com.example.springbootauthdemo.exception;

public class UserInfoEmptyException extends Exception {
    public UserInfoEmptyException(String message) {
        super(message);
    }
}
