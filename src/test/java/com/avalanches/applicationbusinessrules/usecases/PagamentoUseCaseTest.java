package com.avalanches.applicationbusinessrules.usecases;

import com.avalanches.enterprisebusinessrules.entities.Pagamento;
import com.avalanches.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches.interfaceadapters.gateways.interfaces.PagamentoGatewayInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.webjars.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoUseCaseTest {

    @Mock
    private PagamentoGatewayInterface pagamentoGateway;

    private PagamentoUseCase pagamentoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pagamentoUseCase = new PagamentoUseCase();
    }

    @Test
    void testCadastrar() {
        // Arrange
        WebhookParams webhook = mock(WebhookParams.class);
        when(webhook.idPedido()).thenReturn(1);
        when(webhook.status()).thenReturn(StatusPagamento.APROVADO);

        // Act
        pagamentoUseCase.cadastrar(webhook, pagamentoGateway);

        // Assert
        ArgumentCaptor<Pagamento> captor = ArgumentCaptor.forClass(Pagamento.class);
        verify(pagamentoGateway).cadastrar(captor.capture());
        Pagamento capturedPagamento = captor.getValue();

        assertEquals(1, capturedPagamento.getIdPedido());
        assertEquals(StatusPagamento.APROVADO, capturedPagamento.getStatusPagamento());
    }

    @Test
    void testEfetuarPagamento_Success() {
        // Arrange
        Integer idPedido = 1;
        when(pagamentoGateway.efetuarPagamento(idPedido)).thenReturn(true);

        // Act
        boolean result = pagamentoUseCase.efetuarPagamento(idPedido, pagamentoGateway);

        // Assert
        assertTrue(result);
        verify(pagamentoGateway).efetuarPagamento(idPedido);
    }

    @Test
    void testEfetuarPagamento_Failure() {
        // Arrange
        Integer idPedido = 1;
        when(pagamentoGateway.efetuarPagamento(idPedido)).thenReturn(false);

        // Act
        boolean result = pagamentoUseCase.efetuarPagamento(idPedido, pagamentoGateway);

        // Assert
        assertFalse(result);
        verify(pagamentoGateway).efetuarPagamento(idPedido);
    }

    @Test
    void testConsultaStatus_ValidId() {
        // Arrange
        Integer idPedido = 1;
        when(pagamentoGateway.verificaPagamentoExiste(idPedido)).thenReturn(true);
        when(pagamentoGateway.consultaStatus(idPedido)).thenReturn(StatusPagamento.APROVADO);

        // Act
        StatusPagamento status = pagamentoUseCase.consultaStatus(idPedido, pagamentoGateway);

        // Assert
        assertEquals(StatusPagamento.APROVADO, status);
        verify(pagamentoGateway).verificaPagamentoExiste(idPedido);
        verify(pagamentoGateway).consultaStatus(idPedido);
    }

    @Test
    void testConsultaStatus_InvalidId() {
        // Arrange
        Integer idPedido = 1;
        when(pagamentoGateway.verificaPagamentoExiste(idPedido)).thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                pagamentoUseCase.consultaStatus(idPedido, pagamentoGateway)
        );
        assertEquals("Pagamento não encontrado.", exception.getMessage());
        verify(pagamentoGateway).verificaPagamentoExiste(idPedido);
        verify(pagamentoGateway, never()).consultaStatus(anyInt());
    }

    @Test
    void testMontarPagamento() {
        // Arrange
        WebhookParams webhook = mock(WebhookParams.class);
        when(webhook.idPedido()).thenReturn(2);
        when(webhook.status()).thenReturn(StatusPagamento.APROVADO);

        // Act
        pagamentoUseCase.cadastrar(webhook, pagamentoGateway);

        // Assert
        ArgumentCaptor<Pagamento> captor = ArgumentCaptor.forClass(Pagamento.class);
        verify(pagamentoGateway).cadastrar(captor.capture());
        Pagamento capturedPagamento = captor.getValue();

        assertEquals(2, capturedPagamento.getIdPedido());
        assertEquals(StatusPagamento.APROVADO, capturedPagamento.getStatusPagamento());
    }
}
