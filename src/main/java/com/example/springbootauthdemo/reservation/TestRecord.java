package com.example.springbootauthdemo.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "test_records")
public class TestRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_record_id")
    private Long id;

    @OneToMany
    @JoinColumn(name = "subtest_record_id")
    private List<SubTestRecord> subTestRecordList = new ArrayList<>();

}
