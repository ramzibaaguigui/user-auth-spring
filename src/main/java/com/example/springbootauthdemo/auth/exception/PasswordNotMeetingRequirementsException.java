package com.example.springbootauthdemo.auth.exception;

public class PasswordNotMeetingRequirementsException extends Exception {

    public PasswordNotMeetingRequirementsException(String s) {
        super(s);
    }
}
