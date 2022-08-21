package com.example.springbootauthdemo.payload;

import lombok.Getter;

@Getter
public class LoginRequestPayload {
    private String username;
    private String password;

}
