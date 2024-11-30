package com.avalanches.frameworksanddrivers.api;

import com.avalanches.applicationbusinessrules.usecases.PagamentoUseCase;
import com.avalanches.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches.frameworksanddrivers.api.dto.WebHookMockParams;
import com.avalanches.frameworksanddrivers.databases.config.BancoDeDadosContexto;
import com.avalanches.interfaceadapters.controllers.PagamentoController;
import com.avalanches.interfaceadapters.presenters.dtos.WebHookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PagamentoApiTest {

    @Mock
    private BancoDeDadosContexto bancoDeDadosContexto;

    @Spy
    private PagamentoController pagamentoController;

    @Mock
    private PagamentoUseCase pagamentoUseCase;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PagamentoApi pagamentoApi;

    @Spy
    private PagamentoApi pagamentoApiSpy;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations for mocks and inject mocks
        MockitoAnnotations.openMocks(this);
        when(bancoDeDadosContexto.getJdbcTemplate()).thenReturn(jdbcTemplate);
        ReflectionTestUtils.setField(pagamentoApiSpy, "bancoDeDadosContexto", bancoDeDadosContexto);
        doReturn(pagamentoController).when(pagamentoApiSpy).createPagamentoController();
    }

    @Test
    void testWebhook_Success() {
        // Mocking input parameters
        WebhookParams webhookParams = new WebhookParams(123, StatusPagamento.APROVADO);

        // Mock WebHookMockParams (if required)
        WebHookMockParams webHookMockParams = mock(WebHookMockParams.class);

        // Simulate controller behavior

        doNothing().when(pagamentoController).webhook(eq(webhookParams), eq(bancoDeDadosContexto), any(WebHookMockParams.class));

        // Call the method under test
        ResponseEntity<WebHookDto> response = pagamentoApiSpy.webhook(webhookParams);

        // Verify controller invocation
        verify(pagamentoController, times(1)).webhook(eq(webhookParams), eq(bancoDeDadosContexto), any(WebHookMockParams.class));

        // Validate response
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().sucesso());
        assertEquals("Webhook recebido com sucesso", response.getBody().mensagem());
    }

    @Test
    void testWebhook_Exception() {
        // Mocking input parameters
        WebhookParams webhookParams = new WebhookParams(123, StatusPagamento.APROVADO);

        // Simulate controller throwing an exception
        doThrow(new RuntimeException("Webhook processing failed")).when(pagamentoController)
                .webhook(eq(webhookParams), eq(bancoDeDadosContexto), any(WebHookMockParams.class));

        // Call the method under test
        ResponseEntity<WebHookDto> response = pagamentoApiSpy.webhook(webhookParams);

        // Verify controller invocation
        verify(pagamentoController, times(1)).webhook(eq(webhookParams), eq(bancoDeDadosContexto), any(WebHookMockParams.class));

        // Validate response
        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        assertFalse(response.getBody().sucesso());
        assertEquals("Ocorreu um erro ao processar o webhook", response.getBody().mensagem());
    }

}
