package com.example.springbootauthdemo.auth.filter;

import com.example.springbootauthdemo.auth.headers.AuthHeaders;
import com.example.springbootauthdemo.auth.entity.UserAuth;
import com.example.springbootauthdemo.auth.security.auth.AnonymousAuthentication;
import com.example.springbootauthdemo.auth.security.auth.TokenAuthentication;
import com.example.springbootauthdemo.auth.utils.TimeUtils;
import com.example.springbootauthdemo.lab.LabAuth;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthFilter extends OncePerRequestFilter {

    private final TimeUtils time;
    private final AuthenticationManager authenticationManager;

    public UserAuthFilter(AuthenticationManager authenticationManager, TimeUtils time) {
        this.authenticationManager = authenticationManager;
        this.time = time;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authToken = extractAuthTokenHeader(request);

        if (authToken == null) {
            System.out.println("authToken == null");
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("passed one");
        Authentication auth = authenticationManager.authenticate(TokenAuthentication.create(authToken));

        if (auth instanceof AnonymousAuthentication) {

            filterChain.doFilter(request, response);
            System.out.println("auth instanceof AnonymousAuthentication");
            return;
        }
        //

        //
        if (auth instanceof UserAuth) {
            auth.setAuthenticated(!authExpired(((UserAuth) auth)));

        }
        if (auth instanceof LabAuth) {
            auth.setAuthenticated(!labAuthExpired(((LabAuth) auth)));
        }

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return false;
    }


    private String extractAuthTokenHeader(HttpServletRequest request) {
        return request.getHeader(AuthHeaders.HEADER_AUTHENTICATION);
    }

    private boolean authExpired(@NonNull UserAuth userAuth) {
        return userAuth.getExpiresAt().before(time.now());
    }

    private boolean labAuthExpired(@NonNull LabAuth labAuth) {
        return  labAuth.getExpiresAt().before(time.now());
    }
}
