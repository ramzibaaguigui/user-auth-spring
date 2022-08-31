package com.example.springbootauthdemo.controller;

import com.example.springbootauthdemo.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin
public class WebController {



    @GetMapping("/authRequired")
    public ResponseEntity<?> authRequired(Authentication auth) {
        return ResponseEntity.ok("hello, the authentication is required in order to access this content\n" +
                "You are current logged as: " +
                ((User) auth.getPrincipal()));
    }

    @GetMapping("/forAll")
    public ResponseEntity<?> forAll() {
        return ResponseEntity.ok("this content is available for all");
    }
}
