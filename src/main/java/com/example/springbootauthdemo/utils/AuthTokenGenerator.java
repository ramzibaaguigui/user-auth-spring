package com.example.springbootauthdemo.utils;

import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenGenerator {
    private static final int AUTH_TOKEN_LENGTH = 64;

    public String generateToken() {
        return RandomString.make(AUTH_TOKEN_LENGTH);
    }
}
