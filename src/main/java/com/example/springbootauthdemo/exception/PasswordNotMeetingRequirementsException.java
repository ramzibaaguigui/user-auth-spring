package com.example.springbootauthdemo.exception;

public class PasswordNotMeetingRequirementsException extends Exception {

    public PasswordNotMeetingRequirementsException(String s) {
        super(s);
    }
}
