package com.example.springbootauthdemo.reservation;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReservationRequestPayload {
    private Date date;
    private List<Long> testIds = new ArrayList<>();

}
