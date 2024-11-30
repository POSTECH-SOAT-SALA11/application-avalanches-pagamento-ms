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

    private final OkHttpClient httpClient = new OkHttpClient();

    public String getBaseUrl() {
        return ambiente.getProperty("pagamento.webhook.url");
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
}
