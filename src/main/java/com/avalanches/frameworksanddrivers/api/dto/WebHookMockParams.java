package com.avalanches.frameworksanddrivers.api.dto;

import jakarta.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class WebHookMockParams {

    @Autowired
    protected Environment ambiente;

    private String baseUrl;
    private final OkHttpClient httpClient = new OkHttpClient();

    @PostConstruct
    protected void init() {
        // Retrieve the property and throw an exception if it is not found
        baseUrl = ambiente.getProperty("pagamento.webhook.url");
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalArgumentException("Property 'pagamento.webhook.url' is not defined in the environment or configuration file.");
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
}
