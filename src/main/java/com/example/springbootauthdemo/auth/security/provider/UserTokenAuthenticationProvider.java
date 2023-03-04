package com.example.springbootauthdemo.auth.security.provider;

import com.example.springbootauthdemo.auth.entity.UserAuth;
import com.example.springbootauthdemo.auth.repository.UserAuthRepository;
import com.example.springbootauthdemo.auth.security.auth.TokenAuthentication;
import com.example.springbootauthdemo.auth.service.UserAuthPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UserTokenAuthenticationProvider implements AuthenticationProvider {

    private final UserAuthPool userAuthPool;
    private final UserAuthRepository userAuthRepository;

    @Autowired
    public UserTokenAuthenticationProvider(UserAuthPool userAuthPool,
                                           UserAuthRepository userAuthRepository) {
        this.userAuthPool = userAuthPool;
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(TokenAuthentication.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        if (authentication == null) {
            return null;
        }

        final String authToken = ((String) authentication.getCredentials());
        if (authToken == null) {
            return null;
        }

        UserAuth auth = userAuthPool.validateAuthentication(authToken);
        if (auth == null) {
            auth = userAuthRepository.findUserAuthByTokenEquals(authToken).orElse(null);
            if (auth == null) {
                return null;
            }

            userAuthPool.storeAuthentication(auth);
        }

        return auth;
    }


}
