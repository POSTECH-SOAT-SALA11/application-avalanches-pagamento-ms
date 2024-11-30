package com.avalanches_pagamento.frameworksanddrivers.api.dto;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebHookMockParamsTest {

    private OkHttpClient mockHttpClient;

    @BeforeEach
    void setUp() {
        mockHttpClient = new OkHttpClient();
    }

    @Test
    void testConstructor_WhenValidParametersAreProvided() {
        // Arrange
        String webhookUrl = "http://sistema-pagamentos-mock-service:5001/pagamento/";

        // Act
        WebHookMockParams webHookMockParams = new WebHookMockParams(webhookUrl, mockHttpClient);

        // Assert
        assertEquals(
                webhookUrl,
                webHookMockParams.baseUrl,
                "Base URL should match the provided webhook URL."
        );
        assertNotNull(webHookMockParams.httpClient, "HttpClient should not be null");
    }

    @Test
    void testConstructor_WhenWebhookUrlIsNull_ThrowsException() {
        // Arrange
        String webhookUrl = null;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () ->
                new WebHookMockParams(webhookUrl, mockHttpClient)
        );
        assertEquals("webhookUrl cannot be null", exception.getMessage());
    }

    @Test
    void testConstructor_WhenWebhookUrlIsEmpty_ThrowsException() {
        // Arrange
        String webhookUrl = "";

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new WebHookMockParams(webhookUrl, mockHttpClient)
        );
        assertEquals("webhookUrl cannot be empty", exception.getMessage());
    }

    @Test
    void testConstructor_WhenHttpClientIsNull_ThrowsException() {
        // Arrange
        OkHttpClient httpClient = null;

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () ->
                new WebHookMockParams("http://sistema-pagamentos-mock-service:5001/pagamento/", httpClient)
        );
        assertEquals("httpClient cannot be null", exception.getMessage());
    }
}
