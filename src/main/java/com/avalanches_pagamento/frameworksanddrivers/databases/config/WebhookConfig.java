package com.avalanches_pagamento.frameworksanddrivers.databases.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import okhttp3.OkHttpClient;

@Configuration
public class WebhookConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebhookConfig.class);

    private final Environment environment;

    public WebhookConfig(Environment environment) {
        this.environment = environment;
        logger.info("Initializing WebhookConfig");
    }

    @Bean
    public OkHttpClient getHttpClient() {
        logger.info("Creating OkHttpClient bean");
        return new OkHttpClient();
    }

    @Bean
    public String webhookUrl() {
        String url = environment.getProperty("pagamento.webhook.url");
        if (url == null || url.isEmpty()) {
            logger.warn("The property 'pagamento.webhook.url' is not set or is empty!");
        } else {
            logger.debug("The webhook URL is: {}", url);
        }
        return url;
    }
}
