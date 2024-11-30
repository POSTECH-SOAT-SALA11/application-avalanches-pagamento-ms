package com.avalanches_pagamento.applicationbusinessrules.usecases;

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
class PagamentoUseCaseTest {

    @Mock
    private PagamentoGatewayInterface pagamentoGateway;

    @Mock
    private WebhookParams webhook;

    @InjectMocks
    private PagamentoUseCase pagamentoUseCase;

    @BeforeEach
    void setUp() {
        pagamentoUseCase = new PagamentoUseCase();
    }

    @Test
    void testCadastrar() {
        // Arrange
        Integer idPedido = 1;
        StatusPagamento statusPagamento = StatusPagamento.APROVADO;

        when(webhook.idPedido()).thenReturn(idPedido);
        when(webhook.status()).thenReturn(statusPagamento);

        // Act
        pagamentoUseCase.cadastrar(webhook, pagamentoGateway);

        // Assert
        verify(pagamentoGateway).cadastrar(any());
    }

    @Test
    void testEfetuarPagamento() {
        // Arrange
        Integer idPedido = 1;
        when(pagamentoGateway.efetuarPagamento(idPedido)).thenReturn(true);

        // Act
        Boolean result = pagamentoUseCase.efetuarPagamento(idPedido, pagamentoGateway);

        // Assert
        assertTrue(result);
        verify(pagamentoGateway).efetuarPagamento(idPedido);
    }

    @Test
    void testConsultaStatus_PagamentoExistente() {
        // Arrange
        Integer idPedido = 1;
        StatusPagamento statusPagamento = StatusPagamento.APROVADO;

        when(pagamentoGateway.verificaPagamentoExiste(idPedido)).thenReturn(true);
        when(pagamentoGateway.consultaStatus(idPedido)).thenReturn(statusPagamento);

        // Act
        StatusPagamento result = pagamentoUseCase.consultaStatus(idPedido, pagamentoGateway);

        // Assert
        assertEquals(statusPagamento, result);
        verify(pagamentoGateway).consultaStatus(idPedido);
    }

    @Test
    void testConsultaStatus_PagamentoNaoExistente() {
        // Arrange
        Integer idPedido = 1;

        when(pagamentoGateway.verificaPagamentoExiste(idPedido)).thenReturn(false);

        // Act & Assert
        assertThrows(org.webjars.NotFoundException.class, () -> pagamentoUseCase.consultaStatus(idPedido, pagamentoGateway));

        verify(pagamentoGateway).verificaPagamentoExiste(idPedido);
    }
}
