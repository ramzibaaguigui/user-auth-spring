package com.example.springbootauthdemo.lab;

import com.example.springbootauthdemo.auth.entity.User;
import com.example.springbootauthdemo.auth.exception.AuthenticationTokenNotFoundException;
import com.example.springbootauthdemo.auth.exception.IncorrectUsernamePasswordException;
import com.example.springbootauthdemo.auth.exception.LoginRequestPayloadNotValidException;
import com.example.springbootauthdemo.auth.headers.AuthHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class LabAuthController {

    private LabService labService;
    private final LabAuthService labAuthService;
    private LabAuthPool labAuthPool;

    @Autowired
    public LabAuthController(LabService labService, LabAuthService labAuthService, LabAuthPool labAuthPool) {
        this.labService = labService;
        this.labAuthService = labAuthService;
        this.labAuthPool = labAuthPool;
    }


    @PostMapping("lab/register")
    public ResponseEntity<?> register(@RequestBody Laboratory createLabPayload) {
        try {
            Laboratory laboratory = labService.registerLaboratory(createLabPayload);
            return ResponseEntity.ok(laboratory);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public LabAuthController(LabAuthService labAuthService) {
        this.labAuthService = labAuthService;
    }
    @PostMapping("/lab/auth")
    public ResponseEntity<?> authenticate(@RequestBody LabLoginRequest labLoginRequest) {
        System.out.println("getting to this point");
        try {
            return ResponseEntity.ok(labService.authenticateLab(labLoginRequest));
        } catch (Exception | LoginRequestPayloadNotValidException | IncorrectUsernamePasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/lab/signout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        String authToken = httpServletRequest.getHeader(AuthHeaders.HEADER_AUTHENTICATION);
        System.out.println("=======================handling the response=========================");
        System.out.println("the logout is requested");
        if (authToken == null) {
            System.out.println("the auth is null");
            return ResponseEntity.badRequest().build();
        }

        try {
            labAuthService.removeAuthentication(authToken);
            labAuthPool.removeAuthentication(authToken);
            return ResponseEntity.ok().build();
        } catch (AuthenticationTokenNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
