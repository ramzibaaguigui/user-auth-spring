package com.example.springbootauthdemo.reservation;

import ch.qos.logback.classic.pattern.MessageConverter;
import lombok.SneakyThrows;
import nonapi.io.github.classgraph.json.JSONDeserializer;
import nonapi.io.github.classgraph.json.JSONSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.net.URISyntaxException;


@Service
public class TestResultInterpretationService {
    /*
    we are going to have an http client that connects with the other service to provide test result interpretation

     */
    // to get from abdelkhalek
    // todo: continue with the ai server connectoin
    private static final String INTERPRETATION_SERVER_URL = "http://127.0.0.1:8000/api/interpret/";

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

        // getting the response from the databse
        InterpretationServerResponse response = getInterpretationFromServerResponse(
                testRecordToString(testRecord)
        );

        TestResultInterpretation interpretation = new TestResultInterpretation();
        interpretation.setTestRecord(testRecord);
        interpretation.setTechnicalContent(response.getTechnical());
        interpretation.setNonTechnicalContent(response.getNonTechnical());
        testRecordRepository.save(testRecord);
        return interpretationRepository.save(interpretation);
    }


    private InterpretationServerResponse getInterpretationFromServerResponse(String requestContent) throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-cache");
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity<String> request = new HttpEntity<>(
                JSONSerializer.serializeObject(InterpretationServerRequestBody.create(requestContent)),
                headers
        );

        InterpretationServerResponse response = restTemplate.postForObject(
                INTERPRETATION_SERVER_URL,
                request,
                InterpretationServerResponse.class);
        return response;

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
