package com.example.springbootauthdemo.auth.security.config;

import com.example.springbootauthdemo.auth.filter.UserAuthFilter;
import com.example.springbootauthdemo.auth.security.provider.AnonymousAuthenticationProvider;
import com.example.springbootauthdemo.auth.security.provider.LabTokenAuthenticationProvider;
import com.example.springbootauthdemo.auth.security.provider.UserTokenAuthenticationProvider;
import com.example.springbootauthdemo.auth.utils.TimeUtils;
import com.example.springbootauthdemo.lab.LabAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserTokenAuthenticationProvider userTokenAuthenticationProvider;
    private AnonymousAuthenticationProvider anonymousAuthenticationProvider;
    private LabTokenAuthenticationProvider labTokenAuthenticationProvider;
    private TimeUtils time;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void setTokenAuthenticationProvider(UserTokenAuthenticationProvider userTokenAuthenticationProvider) {
        this.userTokenAuthenticationProvider = userTokenAuthenticationProvider;
    }

    @Autowired
    public void setAnonymousAuthenticationProvider(AnonymousAuthenticationProvider anonymousAuthenticationProvider) {
        this.anonymousAuthenticationProvider = anonymousAuthenticationProvider;
    }

    @Autowired
    public void setLabTokenAuthenticationProvider(LabTokenAuthenticationProvider labTokenAuthenticationProvider)  {
        this.labTokenAuthenticationProvider = labTokenAuthenticationProvider;
    }
    @Autowired
    public void setTimeUtils(TimeUtils time) {
        this.time = time;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new UserAuthFilter(authenticationManager(), time), BasicAuthenticationFilter.class);
        // http.addFilterBefore(new LabAuthFilter(authenticationManager(), time), BasicAuthenticationFilter.class);
        http.authorizeRequests().antMatchers("/swagger-ui").permitAll();
        http.authorizeRequests().antMatchers("/forAll", "/patient/auth", "/patient/register",
                        "/lab/auth", "/lab/register")
                .permitAll().and()
                .authorizeRequests().antMatchers("lab/signout").hasRole("LAB")
                .and()
                .authorizeRequests().antMatchers("/patient/signout").hasRole("USER")
                .and().authorizeRequests().antMatchers("/authRequired").authenticated()
                .and().cors().and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userTokenAuthenticationProvider);
        auth.authenticationProvider(labTokenAuthenticationProvider);
        auth.authenticationProvider(anonymousAuthenticationProvider);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}

