package com.pepper.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class OKHttpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OKHttpService.class);

    private final OkHttpClient okHttpClient;

    public OKHttpService(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @CircuitBreaker(name="circuitBreakInstanceTest", fallbackMethod = "fallBack")
    public Response executeGet(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        return call.execute();
    }

    public Response fallBack(String url, Throwable exception) throws Throwable {
        LOGGER.info("Fall Back - State change " + exception.getMessage());
        throw exception;
    }
}
