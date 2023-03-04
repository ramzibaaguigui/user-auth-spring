package com.example.springbootauthdemo.lab;


import com.example.springbootauthdemo.medicaltest.MedicalTestFamily;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "labs")
public class Laboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;

/*
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
*/

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany
    @JoinColumn(name = "family_id")
    private Set<MedicalTestFamily> testFamilies = new HashSet<>();

    public void addTestFamily(MedicalTestFamily testFamily) {
        this.testFamilies.add(testFamily);
    }

    public void removeTestFamily(MedicalTestFamily testFamily) {
        this.testFamilies.remove(testFamily);
    }
}
