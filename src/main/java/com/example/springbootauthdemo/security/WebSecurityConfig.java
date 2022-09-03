package com.example.springbootauthdemo.security;

import com.example.springbootauthdemo.filter.UserAuthFilter;
import com.example.springbootauthdemo.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenAuthenticationProvider tokenAuthenticationProvider;
    private AnonymousAuthenticationProvider anonymousAuthenticationProvider;
    private TimeUtils time;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void setTokenAuthenticationProvider(TokenAuthenticationProvider tokenAuthenticationProvider) {
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
    }

    @Autowired
    public void setAnonymousAuthenticationProvider(AnonymousAuthenticationProvider anonymousAuthenticationProvider) {
        this.anonymousAuthenticationProvider = anonymousAuthenticationProvider;
    }
    @Autowired
    public void setTimeUtils(TimeUtils time) {
        this.time = time;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new UserAuthFilter(authenticationManager(), time), BasicAuthenticationFilter.class);
        http.authorizeRequests().antMatchers("/forAll", "/auth")
                .permitAll().and()
                .authorizeRequests().anyRequest().authenticated()
                .and().cors().and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(tokenAuthenticationProvider);
        auth.authenticationProvider(anonymousAuthenticationProvider);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}

