package com.example.springbootauthdemo.controller;

import com.example.springbootauthdemo.auth.User;
import com.example.springbootauthdemo.payload.LoginRequestPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    public ResponseEntity<?> register(@RequestBody User createUserPayload) {

    }

    public ResponseEntity<?> authenticate(@RequestBody LoginRequestPayload loginRequestPayload) {

    }
}
