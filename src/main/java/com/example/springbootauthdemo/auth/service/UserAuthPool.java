package com.example.springbootauthdemo.auth.service;

import com.example.springbootauthdemo.auth.entity.UserAuth;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserAuthPool {
    private final HashMap<String, UserAuth> authPool = new HashMap<>();

    public boolean storeAuthentication(UserAuth userAuth) {
        if (userAuth == null) {
            return false;
        }
        authPool.put(userAuth.getToken(), userAuth);
        return true;
    }

    public UserAuth validateAuthentication(String token) {
        return authPool.get(token);
    }

    public UserAuth removeAuthentication(String authToken) {
        return authPool.remove(authToken);
    }
}
