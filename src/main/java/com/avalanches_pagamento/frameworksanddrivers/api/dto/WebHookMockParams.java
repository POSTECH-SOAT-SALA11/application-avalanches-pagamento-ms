package com.avalanches_pagamento.frameworksanddrivers.api.dto;

import java.util.Objects;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

@Component
public class WebHookMockParams {

    public final String baseUrl;
    public final OkHttpClient httpClient;

    public WebHookMockParams(String webhookUrl, OkHttpClient httpClient) {
        this.baseUrl = Objects.requireNonNull(webhookUrl, "webhookUrl cannot be null");
        if (webhookUrl.isEmpty()) {
            throw new IllegalArgumentException("webhookUrl cannot be empty");
        }
        this.httpClient = Objects.requireNonNull(httpClient, "httpClient cannot be null");
    }
}
