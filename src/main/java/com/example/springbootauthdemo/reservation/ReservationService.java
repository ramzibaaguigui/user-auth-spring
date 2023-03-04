package com.example.springbootauthdemo.reservation;

import com.example.springbootauthdemo.auth.entity.User;
import com.example.springbootauthdemo.lab.Laboratory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    public Reservation makeReservationByUser(ReservationRequestPayload payload, User patient) {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(payload.getDate());
        reservation.setPatient(patient);
        reservation.setStatus(Reservation.ReservationStatus.RESERVED);
        return this.reservationRepository.save(reservation);
    }

    public Reservation getReservationDetailsForPatient(Long reservationId, User user) {
        return reservationRepository.findAll()
                .stream()
                .filter(reservation -> reservationId.equals(reservationId) && reservation.getPatient().equals(user))
                .findFirst()
                .orElseThrow();
    }

    public Reservation getReservationDetailsForLab(Long reservationId, Laboratory laboratory) {
        return reservationRepository.findAll()
                .stream()
                .filter(reservation -> reservationId.equals(reservationId) && reservation.getLaboratory().equals(laboratory))
                .findFirst()
                .orElseThrow();
    }

    public Reservation approveReservationByLaboratory(Long reservationId, Laboratory laboratory) {
        Reservation reservation = reservationRepository.findAll()
                .stream()
                .filter(res -> res.getId().equals(reservationId) && res.getLaboratory().equals(laboratory))
                .findFirst()
                .orElseThrow();
        reservation.setStatus(Reservation.ReservationStatus.PROGRESS);
        return reservationRepository.save(reservation);
    }


    public Reservation refuseReservationByLab(Long reservationId, Laboratory laboratory) {
        Reservation reservation = reservationRepository.findAll()
                .stream()
                .filter(res -> res.getId().equals(reservationId) && res.getLaboratory().equals(laboratory))
                .findFirst().orElseThrow();
        reservation.setStatus(Reservation.ReservationStatus.REFUSED);
        return reservationRepository.save(reservation);

    }

    public Reservation addTestRecords(Long reservationId, Laboratory laboratory, TestRecord testRecord) {
        Reservation reservation = reservationRepository.findAll()
                .stream().filter(res -> res.getId().equals(reservationId) && res.getLaboratory().equals(laboratory))
                .findFirst().orElseThrow();
        reservation.setTestResult(testRecord);
        return reservationRepository.save(reservation);
    }
}
