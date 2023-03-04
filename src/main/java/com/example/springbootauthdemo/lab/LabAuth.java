package com.example.springbootauthdemo.lab;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "lab_auths")

public class LabAuth implements Authentication {

    @Id
    @Column(name = "lab_auth_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isAuthenticated;

    @ManyToOne
    @JoinColumn(name = "id")
    private Laboratory authLab;

    @Column(name = "issued_at")
    private Date issuedAt;

    @Column(name = "expires_at")
    private Date expiresAt;

    @Column(name = "token")
    private String token;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>() {{
            add(new SimpleGrantedAuthority("ROLE_LAB"));
        }};
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.authLab;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.token;
    }
}
