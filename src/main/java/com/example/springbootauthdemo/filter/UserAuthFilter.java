package com.example.springbootauthdemo.filter;

import com.example.springbootauthdemo.entity.UserAuth;
import com.example.springbootauthdemo.headers.AuthHeaders;
import com.example.springbootauthdemo.service.UserAuthService;
import com.example.springbootauthdemo.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthFilter extends OncePerRequestFilter {

    private final UserAuthService userAuthService;
    private final TimeUtils time;

    @Autowired
    public UserAuthFilter(UserAuthService userAuthService,
                          TimeUtils time) {
        this.userAuthService = userAuthService;
        this.time = time;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
        }

        String authToken = extractAuthTokenHeader(request);
        if (authToken == null) {
            return;
        }

        UserAuth auth = userAuthService.validateAuthentication(authToken);
        if (auth == null) {
            return;
        }

        if (authExpired(auth)) {
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return requestsRegistration(request) || requestsAuthentication(request);
    }

    private boolean requestsRegistration(HttpServletRequest request) {
        return request.getServletPath().equals("/register");
    }

    private boolean requestsAuthentication(HttpServletRequest request) {
        return request.getServletPath().equals("/auth");
    }

    private String extractAuthTokenHeader(HttpServletRequest request) {
        return request.getHeader(AuthHeaders.HEADER_AUTHENTICATION);
    }

    private boolean authExpired(@NonNull UserAuth userAuth) {
        return userAuth.getExpiresAt().before(time.now());
    }
}
