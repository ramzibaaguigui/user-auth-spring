package com.example.springbootauthdemo.medicaltest;

import com.example.springbootauthdemo.lab.Laboratory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MedicalTestController {

    private final MedicalTestService medicalTestService;

    @Autowired
    public MedicalTestController(MedicalTestService medicalTestService) {
        this.medicalTestService = medicalTestService;
    }

    /**
     * This is going to be all about the operations that can happen on the medica test
     * 1- getting the available medical tests family with their embedeed subtests
     * 2- the lab can add a test to the list of its supported tests alongside the price
     * 3- the lab can edit the price of its supported tests
     * 4- the lab can remove the test from the list of its supported tests
     * -- the list of the tests is mainly available in the app
     *
     */


    @GetMapping("/medtest/all/get")
    public ResponseEntity<?> getAllMedicalTests() {
        return ResponseEntity.ok(
                medicalTestService.getAllMedicalTests()
        );
    }

    @PostMapping("/medtest/addtolab/")
    public ResponseEntity<?> addSupportedTestToLab(Authentication labAuth,  List<Long> testIds) {
        try {
            Laboratory authLab = ((Laboratory) labAuth.getPrincipal());
            for (Long testId: testIds){
                medicalTestService.addTestFamilyToLab(testId, authLab);
            }
            return ResponseEntity.ok(authLab);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }


    @DeleteMapping("/medtest/removefromlab/")
    public ResponseEntity<?> removeSupportedTestToLab(Authentication labAuth,  List<Long> testIds) {
        try {
            Laboratory authLab = ((Laboratory) labAuth.getPrincipal());
            for (Long testId: testIds){
                medicalTestService.addTestFamilyToLab(testId, authLab);
            }
            return ResponseEntity.ok(authLab);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }


}
