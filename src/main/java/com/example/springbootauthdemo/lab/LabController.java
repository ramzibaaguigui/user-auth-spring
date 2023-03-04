package com.example.springbootauthdemo.lab;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LabController {

    @GetMapping("/lab/request/all")
    public ResponseEntity<?> getAllAnalysisRequests(Authentication authentication) {
        return null;
    }

    @PostMapping("/lab/request/validate")
    public ResponseEntity<?> validateRequest() {
        return null;
    }

    @PostMapping("/lab/request/post/result")
    public ResponseEntity<?> postTestResult() {
        return null;
    }
}
