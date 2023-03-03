package com.example.springbootauthdemo.auth.payload;

import lombok.Getter;

@Getter
public class LoginRequestPayload {
    private String username;
    private String password;

}
