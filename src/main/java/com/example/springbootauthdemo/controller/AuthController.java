package com.example.springbootauthdemo.controller;

import com.example.springbootauthdemo.entity.User;
import com.example.springbootauthdemo.exception.AuthenticationTokenNotFoundException;
import com.example.springbootauthdemo.exception.IncorrectUsernamePasswordException;
import com.example.springbootauthdemo.exception.LoginRequestPayloadNotValidException;
import com.example.springbootauthdemo.headers.AuthHeaders;
import com.example.springbootauthdemo.payload.LoginRequestPayload;
import com.example.springbootauthdemo.service.UserAuthPool;
import com.example.springbootauthdemo.service.UserAuthService;
import com.example.springbootauthdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class AuthController {

    private final UserService userService;
    private final UserAuthService userAuthService;
    private final UserAuthPool userAuthPool;

    @Autowired
    public AuthController(UserAuthService userAuthService,
                          UserService userService,
                          UserAuthPool userAuthPool) {
        this.userService = userService;
        this.userAuthService = userAuthService;
        this.userAuthPool = userAuthPool;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User createUserPayload) {
        try {
            User user = userService.registerUser(createUserPayload);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestPayload loginRequestPayload) {
        try {
            return ResponseEntity.ok(userService.authenticateUser(loginRequestPayload));
        } catch (Exception | LoginRequestPayloadNotValidException | IncorrectUsernamePasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logouta")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        String authToken = httpServletRequest.getHeader(AuthHeaders.HEADER_AUTHENTICATION);
        System.out.println("=======================handling the response=========================");
        System.out.println("the logout is requested");
        if (authToken == null) {
            System.out.println("the auth is null");
            return ResponseEntity.badRequest().build();
        }

        try {
            userAuthService.removeAuthentication(authToken);
            userAuthPool.removeAuthentication(authToken);
            return ResponseEntity.ok().build();
        } catch (AuthenticationTokenNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
