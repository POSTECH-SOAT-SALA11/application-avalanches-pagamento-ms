package com.avalanches.interfaceadapters.controllers;

import com.avalanches.applicationbusinessrules.usecases.PagamentoUseCase;
import com.avalanches.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches.frameworksanddrivers.api.dto.WebHookMockParams;
import com.avalanches.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches.frameworksanddrivers.databases.interfaces.BancoDeDadosContextoInterface;
import com.avalanches.interfaceadapters.gateways.PagamentoGateway;
import com.avalanches.interfaceadapters.gateways.interfaces.PagamentoGatewayInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.webjars.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PagamentoControllerTest {

    @Mock
    private BancoDeDadosContextoInterface bancoDeDadosContexto;

    @Mock
    private WebHookMockParams webHookMockParams;

    @Mock
    private PagamentoUseCase pagamentoUseCase;

    @Mock
    private PagamentoGateway pagamentoGateway;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PagamentoController pagamentoController;

    @Spy
    private PagamentoController pagamentoControllerSpy;  // Spy for controller to mock createPagamentoUseCase

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(bancoDeDadosContexto.getJdbcTemplate()).thenReturn(jdbcTemplate);
        pagamentoController = new PagamentoController();  // No changes here, keep it as per the original
    }

    @Test
    void testWebhook() {
        // Arrange
        WebhookParams webhookParams = new WebhookParams(1, StatusPagamento.APROVADO);
        doReturn(pagamentoUseCase).when(pagamentoControllerSpy).createPagamentoUseCase();  // Mock method

        // Mock the 'cadastrar' method to prevent actual execution
        doNothing().when(pagamentoUseCase).cadastrar(any(WebhookParams.class), any(PagamentoGatewayInterface.class));

        // Act
        pagamentoControllerSpy.webhook(webhookParams, bancoDeDadosContexto, webHookMockParams);

        // Assert
        verify(pagamentoUseCase).cadastrar(any(WebhookParams.class), any(PagamentoGatewayInterface.class));
    }

    @Test
    void testConsultaStatus_Found() {
        // Arrange
        Integer idPedido = 1;
        StatusPagamento status = StatusPagamento.APROVADO;

        doReturn(pagamentoUseCase).when(pagamentoControllerSpy).createPagamentoUseCase();  // Mock method

        doReturn(pagamentoGateway).when(pagamentoControllerSpy).createPagamentoGateway(bancoDeDadosContexto, webHookMockParams);  // Mock method

        // Mock behavior for the gateway to simulate a valid payment
        when(pagamentoGateway.verificaPagamentoExiste(eq(idPedido))).thenReturn(true);
        when(pagamentoUseCase.consultaStatus(eq(idPedido), eq(pagamentoGateway))).thenReturn(status);
        when(pagamentoGateway.consultaStatus(eq(idPedido))).thenReturn(status);

        // Act
        StatusPagamento result = pagamentoControllerSpy.consultaStatus(idPedido, bancoDeDadosContexto, webHookMockParams);

        // Assert
        assertEquals(status, result);
    }

    @Test
    void testConsultaStatus_NotFound() {
        // Arrange
        Integer idPedido = 1;

        // Mock the controller methods
        doReturn(pagamentoUseCase).when(pagamentoControllerSpy).createPagamentoUseCase();  // Mock method

        PagamentoGateway pagamentoGateway = mock(PagamentoGateway.class);
        doReturn(pagamentoGateway).when(pagamentoControllerSpy).createPagamentoGateway(bancoDeDadosContexto, webHookMockParams);  // Mock method

        // Mock behavior for the gateway to simulate a non-existent payment
        when(pagamentoGateway.verificaPagamentoExiste(eq(idPedido))).thenReturn(false);
        when(pagamentoUseCase.consultaStatus(eq(idPedido), eq(pagamentoGateway))).thenThrow(new NotFoundException("Pagamento não encontrado."));

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                pagamentoControllerSpy.consultaStatus(idPedido, bancoDeDadosContexto, webHookMockParams)
        );
        assertEquals("Pagamento não encontrado.", exception.getMessage());
    }

}
