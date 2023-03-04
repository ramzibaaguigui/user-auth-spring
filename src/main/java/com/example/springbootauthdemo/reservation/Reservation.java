package com.example.springbootauthdemo.reservation;

import com.example.springbootauthdemo.auth.entity.User;
import com.example.springbootauthdemo.lab.Laboratory;
import com.example.springbootauthdemo.medicaltest.MedicalTestFamily;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private ReservationStatus status;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User patient;


    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    private Laboratory laboratory;

    @Column(name = "reservation_date")
    private Date reservationDate;

    @OneToOne
    @JoinColumn(name = "test_record_id")
    @Nullable
    private TestRecord testResult = null;

    @ManyToMany
    @JoinColumn(name = "family_id")
    private List<MedicalTestFamily> targetFamilies = new ArrayList<>();

    enum ReservationStatus {
        RESERVED, PROGRESS, REFUSED, FINISHED
    }
}
