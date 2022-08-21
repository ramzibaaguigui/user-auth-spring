package com.example.springbootauthdemo.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/forAll")
    public ResponseEntity<?> forAll() {
        return ResponseEntity.ok("this content is available for all");
    }

    @GetMapping("/authRequired")
    public ResponseEntity<?> requiresAuth(Authentication auth) {
        String response =
                "You need to be authenticated in order to access this content\n" +
                        "You are currently authenticated as:\n" +
                        auth;
        return ResponseEntity.ok(response);
    }
}
