package com.example.springbootauthdemo;

import com.example.springbootauthdemo.reservation.TestResultInterpretationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootAuthDemoApplicationTests {

    @Autowired
    TestResultInterpretationService testResultInterpretationService;

    @Test
    void contextLoads() {

    }

}
