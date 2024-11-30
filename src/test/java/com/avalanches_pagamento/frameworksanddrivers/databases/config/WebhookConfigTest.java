package com.avalanches_pagamento.frameworksanddrivers.databases.config;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebhookConfigTest {

    @Mock
    private Environment environmentMock;

    @InjectMocks
    private WebhookConfig webhookConfig;

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    void setUp() {
        // Initialize mocks and inject dependencies
        MockitoAnnotations.openMocks(this);

        // Initialize the Spring application context to test the beans
        context = new AnnotationConfigApplicationContext();
        context.register(WebhookConfig.class);
        context.refresh();
    }

    @Test
    void testHttpClientBeanCreation() {
        // Call the bean creation method for OkHttpClient
        OkHttpClient client = webhookConfig.getHttpClient();

        // Verify that the OkHttpClient bean was created successfully
        assertNotNull(client);
    }

    @Test
    void testWebhookUrlWithValidProperty() {
        // Simulate the environment property for a valid webhook URL
        when(environmentMock.getProperty("pagamento.webhook.url")).thenReturn("https://example.com/webhook");

        // Call the bean creation method for webhookUrl
        String webhookUrl = webhookConfig.webhookUrl();

        // Verify that the webhook URL is set correctly
        assertNotNull(webhookUrl);
        assertEquals("https://example.com/webhook", webhookUrl);
    }

    @Test
    void testWebhookUrlWithMissingProperty() {
        // Simulate the environment property for a missing webhook URL
        when(environmentMock.getProperty("pagamento.webhook.url")).thenReturn(null);

        // Call the bean creation method for webhookUrl
        String webhookUrl = webhookConfig.webhookUrl();

        // Verify that the webhook URL is null
        assertNull(webhookUrl);
    }

    @Test
    void testWebhookUrlWithEmptyProperty() {
        // Simulate the environment property for an empty webhook URL
        when(environmentMock.getProperty("pagamento.webhook.url")).thenReturn("");

        // Call the bean creation method for webhookUrl
        String webhookUrl = webhookConfig.webhookUrl();

        // Verify that the webhook URL is empty
        assertEquals("", webhookUrl);
    }
}
