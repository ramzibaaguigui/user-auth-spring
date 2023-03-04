package com.example.springbootauthdemo.auth.security.provider;

import com.example.springbootauthdemo.auth.security.auth.AnonymousAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AnonymousAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new AnonymousAuthentication();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
