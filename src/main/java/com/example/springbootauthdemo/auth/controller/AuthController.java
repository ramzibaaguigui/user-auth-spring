package com.example.springbootauthdemo.auth.controller;

import com.example.springbootauthdemo.auth.entity.User;
import com.example.springbootauthdemo.auth.headers.AuthHeaders;
import com.example.springbootauthdemo.auth.payload.LoginRequestPayload;
import com.example.springbootauthdemo.auth.service.UserAuthPool;
import com.example.springbootauthdemo.auth.service.UserAuthService;
import com.example.springbootauthdemo.auth.exception.AuthenticationTokenNotFoundException;
import com.example.springbootauthdemo.auth.exception.IncorrectUsernamePasswordException;
import com.example.springbootauthdemo.auth.exception.LoginRequestPayloadNotValidException;
import com.example.springbootauthdemo.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("patient/register")
    public ResponseEntity<?> register(@RequestBody User createUserPayload) {
        try {
            User user = userService.registerUser(createUserPayload);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("patient/auth")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestPayload loginRequestPayload) {
        try {
            return ResponseEntity.ok(userService.authenticateUser(loginRequestPayload));
        } catch (Exception | LoginRequestPayloadNotValidException | IncorrectUsernamePasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/patient/signout")
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
