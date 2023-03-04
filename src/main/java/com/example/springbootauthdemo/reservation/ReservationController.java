package com.example.springbootauthdemo.reservation;

import com.example.springbootauthdemo.auth.entity.User;
import com.example.springbootauthdemo.lab.Laboratory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ReservationController {
    /**
     * this interface will be related to every action ralted to reservations
     * 1- getting available time liens
     * 2- creating reservations by users = done
     * 3- getting the information of the reservation ==> done
     * 4- view the reservatin by the lab ==> done
     * 5- approve the reservation
     * 6- refuse the reservation ==> done
     * 7- change the state of the reservation
     * 8- upload the final results of the resevation
     *
     */

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService,
                                 ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
    }


    /**
     *
     * @param authentication
     * @return
     */
    @PostMapping("/reservation/make")
    public ResponseEntity<?> makeReservationByUser(@RequestBody ReservationRequestPayload reservationPayload, Authentication authentication) {
        try {
            User authUser = (User) authentication.getPrincipal();
            return ResponseEntity.ok(reservationService.makeReservationByUser(reservationPayload, authUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/reservation/{reservation_id}/details/bypatient")
    public ResponseEntity<?> getReservationDetailsForPatient(Authentication authentication,
                                                   @PathVariable("reservation_id") Long reservationId) {
        try {
            if (authentication.getPrincipal() instanceof User patient) {
                return ResponseEntity.ok(reservationService.getReservationDetailsForPatient(reservationId, patient));
            }

            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reservation/all/details/bypatient")
    public ResponseEntity<?> getAllReservationsDetailsForPatient(Authentication authentication) {
        try {
            if (authentication.getPrincipal() instanceof User patient) {
                return ResponseEntity.ok(reservationService.getAllReservationsDetailsForPatient(patient));
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reservation/{reservation_id}/details/bylab")
    public ResponseEntity<?> getReservationDetailsForLab(Authentication authentication,
                                                         @PathVariable("reservation_id") Long reservationId) {
        try {
            if (authentication.getPrincipal() instanceof Laboratory laboratory) {
                return ResponseEntity.ok(reservationService.getReservationDetailsForLab(reservationId, laboratory));
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reservation/all/details/bylab")
    public ResponseEntity<?> getAllReservationDetailsForLab(Authentication authentication) {
        try {
            if (authentication.getPrincipal() instanceof Laboratory laboratory) {
                return ResponseEntity.ok(reservationService.getAllReservationsDetailsForLab(laboratory));
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {

            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/reservation/{reservation_id}/approve")
    public ResponseEntity<?> approveReservationByLab(Authentication authentication,
                                                     @PathVariable("reservation_id") Long reservationId) {
        try {
            if (authentication.getPrincipal() instanceof Laboratory laboratory) {
                return ResponseEntity.ok(reservationService.approveReservationByLaboratory(reservationId, laboratory));
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reservation/{reservation_id}/refuse")
    public ResponseEntity<?> refuseReservationByLab(Authentication authentication,
                                                    @PathVariable("reservation_id") Long reservationId) {
        try {
            if (authentication.getPrincipal() instanceof Laboratory laboratory) {
                reservationService.refuseReservationByLab(reservationId, laboratory);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reservation/{reservation_id}/postresult")
    public ResponseEntity<?> uploadTestResult(Authentication authentication,
                                           @PathVariable("reservation_id") Long reservationId,
                                              @RequestBody @NonNull TestRecord testRecord
                                              ) {
        try {
            if (authentication.getPrincipal() instanceof Laboratory laboratory) {
                reservationService.addTestRecords(reservationId, laboratory, testRecord);
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }



}
