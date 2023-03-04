package com.example.springbootauthdemo.medicaltest;

import com.example.springbootauthdemo.lab.Laboratory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "test_family")
public class MedicalTestFamily {


    @Id
    @Column(name = "family_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @OneToMany(mappedBy = "testFamily")
    private List<MedicalSubTest> subTests = new ArrayList<>();

    @ManyToMany(mappedBy = "testFamilies")
    @JsonIgnore
    private List<Laboratory> supportingLaboratories = new ArrayList<>();

}
