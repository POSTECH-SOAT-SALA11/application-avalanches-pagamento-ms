package com.avalanches.frameworksanddrivers.api.dto;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebHookMockParamsTest {

    @Test
    void testGetBaseUrl() {
        // Arrange
        WebHookMockParams webHookMockParams = new WebHookMockParams();

        // Act
        String baseUrl = webHookMockParams.getBaseUrl();

        // Assert
        assertEquals("http://sistema-pagamentos-mock-service:5001/pagamento/", baseUrl, "Base URL should be 'http://sistema-pagamentos-mock-service:5001/pagamento/'");
    }

    @Test
    void testGetHttpClient() {
        // Arrange
        WebHookMockParams webHookMockParams = new WebHookMockParams();

        // Act
        OkHttpClient httpClient = webHookMockParams.getHttpClient();

        // Assert
        assertNotNull(httpClient, "HttpClient should not be null");
        assertTrue(httpClient instanceof OkHttpClient, "HttpClient should be an instance of OkHttpClient");
    }
}
