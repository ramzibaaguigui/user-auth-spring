package com.example.springbootauthdemo.reservation.event;

import com.example.springbootauthdemo.reservation.TestResultInterpretationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestResultUploadedEventListener {

    private final TestResultInterpretationService testResultInterpretationService;

    @Autowired
    public TestResultUploadedEventListener(TestResultInterpretationService interpretationService) {
        this.testResultInterpretationService = interpretationService;
    }

    @EventListener
    public void handleTestResultUploaded(TestResultUploadedEvent testResultUploadedEvent) {

        testResultInterpretationService.interpretTestResults(testResultUploadedEvent.getReservationId());
    }
}
