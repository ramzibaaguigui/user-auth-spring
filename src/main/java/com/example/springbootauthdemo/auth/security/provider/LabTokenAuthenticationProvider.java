package com.example.springbootauthdemo.auth.security.provider;


import com.example.springbootauthdemo.auth.security.auth.TokenAuthentication;
import com.example.springbootauthdemo.lab.LabAuth;
import com.example.springbootauthdemo.lab.LabAuthPool;
import com.example.springbootauthdemo.lab.LabAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class LabTokenAuthenticationProvider implements AuthenticationProvider {

    private final LabAuthPool labAuthPool;
    private final LabAuthRepository labAuthRepository;

    @Autowired
    public LabTokenAuthenticationProvider(LabAuthPool labAuthPool,
                                           LabAuthRepository labAuthRepository) {
        this.labAuthPool = labAuthPool;
        this.labAuthRepository = labAuthRepository;
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

        LabAuth auth = labAuthPool.validateAuthentication(authToken);
        if (auth == null) {
            auth = labAuthRepository.findLabAuthByTokenEquals(authToken).orElse(null);
            if (auth == null) {
                return null;
            }

            labAuthPool.storeAuthentication(auth);
        }

        return auth;
    }

}
