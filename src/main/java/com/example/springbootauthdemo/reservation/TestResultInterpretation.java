package com.example.springbootauthdemo.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "interpretations")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TestResultInterpretation {

    @Id
    @Column(name = "interpretation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "technical_content")
    private String technicalContent;

    @Column(name = "non_technical_content")
    private String nonTechnicalContent;

    @JsonIgnore
    @OneToOne(mappedBy = "interpretation")
    private TestRecord testRecord;


}
