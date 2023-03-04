package com.example.springbootauthdemo.reservation.event;

import com.example.springbootauthdemo.reservation.TestResultInterpretation;
import org.springframework.context.ApplicationEvent;

public class TestResultUploadedEvent extends ApplicationEvent {
    private Long reservationId;


    public TestResultUploadedEvent(Object source, Long reservationId) {
        super(source);
        this.reservationId = reservationId;
    }

    public Long getReservationId() {
        return this.reservationId;
    }

}
