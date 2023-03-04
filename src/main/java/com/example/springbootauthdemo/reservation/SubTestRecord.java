package com.example.springbootauthdemo.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "subtest_records")
public class SubTestRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtest_record_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

}
