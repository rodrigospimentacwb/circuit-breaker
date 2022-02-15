package com.pepper.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${resttemplate.connectionTimeOut}")
    private int timeOut;

    @Value("${resttemplate.readTimeOut}")
    private int readTimeOut;

    @Bean
    public RestTemplate restTemplateClient() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(timeOut))
                .setReadTimeout(Duration.ofMillis(readTimeOut)).build();
    }
}
