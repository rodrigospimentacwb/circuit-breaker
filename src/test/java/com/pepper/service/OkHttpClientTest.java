package com.pepper.service;

import static io.github.resilience4j.circuitbreaker.CircuitBreaker.State.HALF_OPEN;
import static io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreaker.State;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import okhttp3.Response;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Disabled("Run only manual")
class OkHttpClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpClientTest.class);

    @Resource
    CircuitBreakerRegistry circuitBreakerRegistry;

    @Value("${stubby4j.baseUrl}")
    private String baseUrl;

    @Resource
    private OKHttpService okHttpService;

    private final String INCONSISTENT_URL = "timeout";
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

                Response response = okHttpService.executeGet(getUrlBasedOnStateCircuit());

                LOGGER.info("Response: " + new JSONObject(response.body().string()).toString() );

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