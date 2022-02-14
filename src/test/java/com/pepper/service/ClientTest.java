package com.pepper.service;

import javax.annotation.Resource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import okhttp3.Response;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Disabled("Run only manual")
class ClientTest {

    @Value("${stubby4j.baseUrl}")
    private String baseUrl;

    @RepeatedTest(10)
    void excuteGet() throws InterruptedException {

        try {
            Response response = client.executeGet(baseUrl + "circuit-breaker");
            System.out.println(response.isSuccessful());
            System.out.println(response.body().string());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Thread.sleep(1000);
    }

    @Resource
    private Client client;
}