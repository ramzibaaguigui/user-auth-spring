package com.example.springbootauthdemo.auth.exception;

public class UsernameReservedException extends Exception {
    public UsernameReservedException(String s) {
        super(s);
    }
}
