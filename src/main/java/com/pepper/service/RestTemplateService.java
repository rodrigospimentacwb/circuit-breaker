package com.pepper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class RestTemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateService.class);

    private RestTemplate restTemplateClient;

    public RestTemplateService(RestTemplate restTemplateClient) {
        this.restTemplateClient = restTemplateClient;
    }

    @CircuitBreaker(name="circuitBreakInstanceTest", fallbackMethod = "fallBack")
    public <T> ResponseEntity<T> executeGet(HttpMethod method, String url, @Nullable HttpEntity<String> requestEntity, Class<T> responseType) {
        return restTemplateClient.exchange(url, method, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> fallBack(HttpMethod method, String url, @Nullable HttpEntity<String> requestEntity, Class<T> responseType, CallNotPermittedException exception) {
        LOGGER.info("Fall Back - State change: " + exception.getMessage());
        throw exception;
    }
}
