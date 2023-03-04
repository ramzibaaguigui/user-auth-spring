package com.example.springbootauthdemo.reservation;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Service
public class TestResultInterpretationService {
    /*
    we are going to have an http client that connects with the other service to provide test result interpretation

     */
    // to get from abdelkhalek
    // todo: continue with the ai server connectoin
    private static final String INTERPRETATION_SERVER_URL = "";

    private final TestResultInterpretationRepository interpretationRepository;
    private final TestRecordRepository testRecordRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public TestResultInterpretationService(TestResultInterpretationRepository resultInterpretationRepository,
                                           ReservationRepository reservationRepository,
                                           TestRecordRepository testRecordRepository) {
        this.interpretationRepository = resultInterpretationRepository;
        this.reservationRepository = reservationRepository;
        this.testRecordRepository = testRecordRepository;
    }

    @SneakyThrows
    public TestResultInterpretation interpretTestResults(Long reservationId) {
        // creating the http client and connecting to the backend to get the result back
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        TestRecord testRecord = reservation.getTestResult();
        if (testRecord == null) throw new Exception("the test record should never be null");

        // the test result is not null;

        // testRecord.setInterpretation();
        return null;
    }

    private String getInterpretationServerResponse(String requestContent) throws URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        byte[] sampleData = "Sample request body".getBytes();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(INTERPRETATION_SERVER_URL))
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .GET()
                .build();
        return null;
        // todo: some work to be done here
    }



    private String testRecordToString(TestRecord testRecord) {
        StringBuilder stringBuilder = new StringBuilder();
        for (SubTestRecord record: testRecord.getSubTestRecordList()) {
            stringBuilder.append(toStringRecordEntry(record));
        }
        return stringBuilder.toString();
    }

    private String toStringRecordEntry(SubTestRecord subTestRecord) {
        return subTestRecord.getName() + ": " + subTestRecord.getValue() + "\n";
    }


}
