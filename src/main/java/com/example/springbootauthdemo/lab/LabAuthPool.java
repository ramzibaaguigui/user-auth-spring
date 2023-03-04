package com.example.springbootauthdemo.lab;

import com.example.springbootauthdemo.auth.entity.UserAuth;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class LabAuthPool {

    private final HashMap<String, LabAuth> authPool = new HashMap<>();

    public boolean storeAuthentication(LabAuth labAuth) {
        if (labAuth == null) {
            return false;
        }
        authPool.put(labAuth.getToken(), labAuth);
        return true;
    }

    public LabAuth validateAuthentication(String token) {
        return authPool.get(token);
    }

    public LabAuth removeAuthentication(String authToken) {
        return authPool.remove(authToken);
    }

}
