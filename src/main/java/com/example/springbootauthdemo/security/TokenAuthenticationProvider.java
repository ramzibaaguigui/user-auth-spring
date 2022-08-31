package com.example.springbootauthdemo.security;

import com.example.springbootauthdemo.entity.UserAuth;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("authenticate is called");
        if (authentication instanceof UserAuth) {

            System.out.println("the authentication is an instance");
            System.out.println((UserAuth) authentication);
        }
        return authentication;
    }
}
