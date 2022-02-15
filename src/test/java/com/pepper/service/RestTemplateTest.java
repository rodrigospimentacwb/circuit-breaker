package com.pepper.service;

import static io.github.resilience4j.circuitbreaker.CircuitBreaker.State.HALF_OPEN;
import static io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN;

import javax.annotation.Resource;

import com.pepper.model.RestTemplateJson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreaker.State;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Disabled("Run only manual")
class RestTemplateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateTest.class);

    @Resource
    CircuitBreakerRegistry circuitBreakerRegistry;

    @Resource
    private RestTemplateService restTemplateService;

    @Value("${stubby4j.baseUrl}")
    private String baseUrl;

    private final String INCONSISTENT_URL = "inconsistentURL";
    private final String SUCCESS_URL = "successUrl";
    private CircuitBreaker circuitBreaker;

    @BeforeEach
    public void beforeEach() {
        circuitBreaker = circuitBreakerRegistry.circuitBreaker("circuitBreakInstanceTest");
    }

    @Test
    void execute() throws InterruptedException {

        for (int i = 0; i < 100; i++) {

            try {

                LOGGER.info("Circuit-Break State: " + circuitBreaker.getState());

                ResponseEntity<RestTemplateJson> response = restTemplateService.executeGet(HttpMethod.GET, getUrlBasedOnStateCircuit(), null, RestTemplateJson.class);

                LOGGER.info("Response: " + response.toString());

            } catch (Exception e) {
                LOGGER.info("Exception: " + e.getMessage());
            }

            Thread.sleep(1000);
        }
    }

    public String getUrlBasedOnStateCircuit() {

        State state = circuitBreaker.getState();
        if (state.equals(OPEN) || state.equals(HALF_OPEN)) {
            return baseUrl + SUCCESS_URL;
        }

        return baseUrl + INCONSISTENT_URL;
    }
}