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
        MockitoAnnotations.openMocks(this);

        context = new AnnotationConfigApplicationContext();
        context.register(WebhookConfig.class);
        context.refresh();
    }

    @Test
    void testHttpClientBeanCreation() {
        OkHttpClient client = webhookConfig.getHttpClient();

        assertNotNull(client);
    }

    @Test
    void testWebhookUrlWithValidProperty() {
        when(environmentMock.getProperty("pagamento.webhook.url")).thenReturn("https://example.com/webhook");

        String webhookUrl = webhookConfig.webhookUrl();

        assertNotNull(webhookUrl);
        assertEquals("https://example.com/webhook", webhookUrl);
    }

    @Test
    void testWebhookUrlWithMissingProperty() {
        when(environmentMock.getProperty("pagamento.webhook.url")).thenReturn(null);

        String webhookUrl = webhookConfig.webhookUrl();

        assertNull(webhookUrl);
    }

    @Test
    void testWebhookUrlWithEmptyProperty() {
        when(environmentMock.getProperty("pagamento.webhook.url")).thenReturn("");

        String webhookUrl = webhookConfig.webhookUrl();

        assertEquals("", webhookUrl);
    }
}
