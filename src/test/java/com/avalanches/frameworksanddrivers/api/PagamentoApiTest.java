package com.avalanches.frameworksanddrivers.api;

import com.avalanches.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches.frameworksanddrivers.api.dto.WebHookMockParams;
import com.avalanches.frameworksanddrivers.databases.interfaces.BancoDeDadosContextoInterface;
import com.avalanches.interfaceadapters.controllers.PagamentoController;
import com.avalanches.interfaceadapters.presenters.dtos.WebHookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoApiTest {

    @Mock
    private BancoDeDadosContextoInterface bancoDeDadosContexto;

    @Mock
    private PagamentoController pagamentoController;

    @InjectMocks
    private PagamentoApi pagamentoApi;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations for mocks and inject mocks
        MockitoAnnotations.openMocks(this);
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
        ResponseEntity<WebHookDto> response = pagamentoApi.webhook(webhookParams);

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
        ResponseEntity<WebHookDto> response = pagamentoApi.webhook(webhookParams);

        // Verify controller invocation
        verify(pagamentoController, times(1)).webhook(eq(webhookParams), eq(bancoDeDadosContexto), any(WebHookMockParams.class));

        // Validate response
        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        assertFalse(response.getBody().sucesso());
        assertEquals("Ocorreu um erro ao processar o webhook", response.getBody().mensagem());
    }

    @Test
    void testConsultaStatus_Success() {
        // Mock input parameter
        Integer idPedido = 123;

        // Mocking controller behavior
        when(pagamentoController.consultaStatus(eq(idPedido), eq(bancoDeDadosContexto), any(WebHookMockParams.class)))
                .thenReturn(StatusPagamento.APROVADO);

        // Call the method under test
        ResponseEntity<StatusPagamento> response = pagamentoApi.consultaStatus(idPedido);

        // Verify controller invocation
        verify(pagamentoController, times(1)).consultaStatus(eq(idPedido), eq(bancoDeDadosContexto), any(WebHookMockParams.class));

        // Validate response
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(StatusPagamento.APROVADO, response.getBody());
    }

    @Test
    void testConsultaStatus_Exception() {
        // Mock input parameter
        Integer idPedido = 123;

        // Simulate controller throwing an exception
        when(pagamentoController.consultaStatus(eq(idPedido), eq(bancoDeDadosContexto), any(WebHookMockParams.class)))
                .thenThrow(new RuntimeException("Error while fetching status"));

        // Call the method under test and catch the exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pagamentoApi.consultaStatus(idPedido);
        });

        // Verify controller invocation
        verify(pagamentoController, times(1)).consultaStatus(eq(idPedido), eq(bancoDeDadosContexto), any(WebHookMockParams.class));

        // Validate exception message
        assertEquals("Error while fetching status", exception.getMessage());
    }
}
