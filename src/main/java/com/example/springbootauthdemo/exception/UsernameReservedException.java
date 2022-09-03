package com.example.springbootauthdemo.exception;

public class UsernameReservedException extends Exception {
    public UsernameReservedException(String s) {
        super(s);
    }
}
