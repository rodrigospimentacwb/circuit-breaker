package com.pepper.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class Client {

    private final OkHttpClient okHttpClient;

    public Client(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public Response executeGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        return call.execute();
    }
}
