package com.example.springbootauthdemo.lab;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LabController {
    private LabService labService;

    public LabController(LabService labService) {
        this.labService = labService;
    }

    @GetMapping("/lab/all")
    public ResponseEntity<?> getAllAnalysisRequests(Authentication authentication) {
        return ResponseEntity.ok(labService.getAllLabs());
    }

    @GetMapping("/lab/{lab_id}/details")
    public ResponseEntity<?> getAllAnalysisRequests(@PathVariable("lab_id") Long labId, Authentication authentication) {
        return ResponseEntity.ok(labService.getLabById(labId));
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
