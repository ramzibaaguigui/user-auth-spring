package com.example.springbootauthdemo.auth.controller;

import com.example.springbootauthdemo.reservation.TestResultInterpretationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@CrossOrigin
public class WebController {

    @GetMapping("/authRequired")
    public ResponseEntity<?> authRequired(Authentication auth) {
        System.out.println("getting to this point auth required");
        return ResponseEntity.ok("hello, the authentication is required in order to access this content\n" +
                "You are current logged as: " +
                auth.getPrincipal());
    }

    @Autowired
    TestResultInterpretationService testResultInterpretationService;
    @GetMapping("/forAll")
    public ResponseEntity<?> forAll() throws URISyntaxException {
        System.out.println(testResultInterpretationService.
                getInterpretationFromServerResponse("\"White Blood Cells (WBC)\\t| 8.2 x10^9/L\\t| 4" +
                        ".5-11.0 x10^9/L\\nRed Blood Cells (RBC)\\" +
                        "t| 5.2 x10^12/L\\t| 4.5-5.5 x10^12/L\\nHemoglobin (Hb)\\t| 14" +
                        ".5 g/dL\\t| 13.5-17.5 g/dL\\nHematocrit (Hct)\\t| 44.0%\\t| " +
                        "38.8-50.0%\\nMean Corpuscular Volume (MCV)\\t" +
                        "| 85.0 fL\\t| 80-100 fL\\nMean Corpuscular Hemoglobin (MCH)\\t| 29" +
                        ".0 pg\\t| 27-33 pg\\nMean Corpuscular Hemoglobin Concentration (MCHC)\\t| 34.1 g/dL\\t| 32-36 g/dL\\nRed Cell Distribution Width " +
                        "(RDW)\\t| 12.0%\\t| 11.5-14.5%\\nPlatelets\\t| 200 x10^9/L\\t| 150-450 x10^9/L\\n\"").getNonTechnical());
        return ResponseEntity.ok("this content is available for all");
    }
}
