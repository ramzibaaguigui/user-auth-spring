package com.example.springbootauthdemo.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class WebController {

    @GetMapping("/authRequired")
    public ResponseEntity<?> authRequired(Authentication auth) {
        System.out.println("getting to this point auth required");
        return ResponseEntity.ok("hello, the authentication is required in order to access this content\n" +
                "You are current logged as: " +
                auth.getPrincipal());
    }

    @GetMapping("/forAll")
    public ResponseEntity<?> forAll() {
        return ResponseEntity.ok("this content is available for all");
    }
}
