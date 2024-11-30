package com.avalanches.frameworksanddrivers.api.dto;

import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebHookMockParamsTest {

    @Mock
    private Environment mockEnvironment;

    private WebHookMockParams webHookMockParams;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simulate @Autowired injection
        webHookMockParams = new WebHookMockParams();
        webHookMockParams.ambiente = mockEnvironment;
    }

    @Test
    void testGetBaseUrl_WhenPropertyIsFound() {
        // Arrange
        when(mockEnvironment.getProperty("pagamento.webhook.url"))
                .thenReturn("http://sistema-pagamentos-mock-service:5001/pagamento/");

        // Simulate @PostConstruct
        webHookMockParams.init();

        // Act
        String baseUrl = webHookMockParams.getBaseUrl();

        // Assert
        assertEquals(
                "http://sistema-pagamentos-mock-service:5001/pagamento/",
                baseUrl,
                "Base URL should be 'http://sistema-pagamentos-mock-service:5001/pagamento/'"
        );
    }

    @Test
    void testGetBaseUrl_WhenPropertyIsMissing_ThrowsException() {
        // Arrange
        when(mockEnvironment.getProperty("pagamento.webhook.url")).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, webHookMockParams::init);
        assertEquals(
                "Property 'pagamento.webhook.url' is not defined in the environment or configuration file.",
                exception.getMessage()
        );
    }

    @Test
    void testGetHttpClient() {
        // Act
        OkHttpClient httpClient = webHookMockParams.getHttpClient();

        // Assert
        assertNotNull(httpClient, "HttpClient should not be null");
        assertTrue(httpClient instanceof OkHttpClient, "HttpClient should be an instance of OkHttpClient");
    }
}
