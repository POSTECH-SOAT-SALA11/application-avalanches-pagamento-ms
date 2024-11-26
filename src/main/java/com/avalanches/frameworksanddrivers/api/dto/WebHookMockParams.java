package com.avalanches.frameworksanddrivers.api.dto;

import okhttp3.OkHttpClient;

public class WebHookMockParams {
    private final String baseUrl = "http://sistema-pagamentos-mock-service:5001/pagamento/";
    private final OkHttpClient httpClient = new OkHttpClient();

    public String getBaseUrl() {
        return baseUrl;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
}
