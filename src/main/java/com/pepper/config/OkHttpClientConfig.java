package com.pepper.config;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

@Configuration
public class OkHttpClientConfig {

    @Value("${okhttp.connectionTimeOut}")
    private int timeOut;

    @Value("${okhttp.readTimeOut}")
    private int readTimeOut;

    @Value("${okhttp.maxIdleConnections}")
    private int maxIdleConnections;

    @Value("${okhttp.keepAliveDuration}")
    private int keepAliveDuration;

    @Bean
    public OkHttpClient ohHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(Duration.of(timeOut, ChronoUnit.MILLIS))
                .connectTimeout(Duration.of(readTimeOut, ChronoUnit.MILLIS));
        builder.connectionPool(new ConnectionPool(maxIdleConnections,keepAliveDuration, TimeUnit.SECONDS));
        return builder.build();
    }
}
