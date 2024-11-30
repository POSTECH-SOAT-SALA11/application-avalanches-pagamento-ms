package com.avalanches_pagamento.frameworksanddrivers.api;

import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches_pagamento.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches_pagamento.interfaceadapters.controllers.interfaces.PagamentoControllerInterface;
import com.avalanches_pagamento.interfaceadapters.presenters.dtos.WebHookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagamentoApiTest {

    @Mock
    private PagamentoControllerInterface pagamentoController; // Mock the PagamentoController

    private PagamentoApi pagamentoApi; // The class under test

    private final WebhookParams webhookParams = new WebhookParams(123, StatusPagamento.APROVADO); // Test data

    @BeforeEach
    void setUp() {
        pagamentoApi = new PagamentoApi(pagamentoController);
    }

    @Test
    void testWebhook_Success() {
        // Arrange
        WebHookDto expectedResponse = new WebHookDto(true, "Webhook recebido com sucesso");

        // Act
        ResponseEntity<WebHookDto> response = pagamentoApi.webhook(webhookParams);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.mensagem(), response.getBody().mensagem());

        verify(pagamentoController).webhook(webhookParams);
    }

    @Test
    void testWebhook_InternalServerError() {
        // Arrange
        doThrow(new RuntimeException("Test exception")).when(pagamentoController)
                .webhook(webhookParams);

        WebHookDto expectedResponse = new WebHookDto(false, "Ocorreu um erro ao processar o webhook");

        // Act
        ResponseEntity<WebHookDto> response = pagamentoApi.webhook(webhookParams);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(expectedResponse.mensagem(), response.getBody().mensagem());

        verify(pagamentoController).webhook(webhookParams);
    }

    @Test
    void testConsultaStatus_Success() {
        // Arrange
        StatusPagamento expectedStatus = StatusPagamento.APROVADO;
        when(pagamentoController.consultaStatus(123))
                .thenReturn(expectedStatus);

        // Act
        ResponseEntity<StatusPagamento> response = pagamentoApi.consultaStatus(123);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStatus, response.getBody());

        verify(pagamentoController).consultaStatus(123);
    }

    @Test
    void testEfetuarPagamento_Success() {
        // Arrange
        Boolean expectedResponse = true;
        when(pagamentoController.efetuarPagamento(123))
                .thenReturn(expectedResponse);

        // Act
        ResponseEntity<Boolean> response = pagamentoApi.efetuarPagamento(123);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());

        verify(pagamentoController).efetuarPagamento(123);
    }
}
