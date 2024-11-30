package com.avalanches_pagamento.interfaceadapters.controllers;

import com.avalanches_pagamento.applicationbusinessrules.usecases.interfaces.PagamentoUseCaseInterface;
import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches_pagamento.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches_pagamento.interfaceadapters.gateways.interfaces.PagamentoGatewayInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagamentoControllerTest {

    @Mock
    private PagamentoGatewayInterface pagamentoGateway;

    @Mock
    private PagamentoUseCaseInterface pagamentoUseCase;

    @InjectMocks
    private PagamentoController pagamentoController;

    private WebhookParams webhookParams;

    private Integer idPedido;

    @BeforeEach
    void setUp() {
        idPedido = 123;
        webhookParams = new WebhookParams(idPedido, StatusPagamento.APROVADO);
    }

    @Test
    void testWebhook() {
        // Arrange
        doNothing().when(pagamentoUseCase).cadastrar(webhookParams, pagamentoGateway);

        // Act
        pagamentoController.webhook(webhookParams);

        // Assert
        verify(pagamentoUseCase, times(1)).cadastrar(webhookParams, pagamentoGateway);
    }

    @Test
    void testConsultaStatus() {
        // Arrange
        StatusPagamento expectedStatus = StatusPagamento.APROVADO;
        when(pagamentoUseCase.consultaStatus(idPedido, pagamentoGateway)).thenReturn(expectedStatus);

        // Act
        StatusPagamento actualStatus = pagamentoController.consultaStatus(idPedido);

        // Assert
        assertEquals(expectedStatus, actualStatus);
        verify(pagamentoUseCase, times(1)).consultaStatus(idPedido, pagamentoGateway);
    }

    @Test
    void testEfetuarPagamento() {
        // Arrange
        Boolean expectedResponse = true;
        when(pagamentoUseCase.efetuarPagamento(idPedido, pagamentoGateway)).thenReturn(expectedResponse);

        // Act
        Boolean actualResponse = pagamentoController.efetuarPagamento(idPedido);

        // Assert
        assertTrue(actualResponse);
        verify(pagamentoUseCase, times(1)).efetuarPagamento(idPedido, pagamentoGateway);
    }
}
