package com.avalanches_pagamento.interfaceadapters.gateways;

import com.avalanches_pagamento.enterprisebusinessrules.entities.Pagamento;
import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches_pagamento.frameworksanddrivers.api.dto.WebHookMockParams;
import com.avalanches_pagamento.frameworksanddrivers.databases.interfaces.BancoDeDadosContextoInterface;
import io.lettuce.core.api.sync.RedisCommands;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoGatewayTest {

    @Mock
    private BancoDeDadosContextoInterface bancoDeDadosContexto;

    @Mock
    private RedisCommands<String, String> redisCommands;

    @Mock
    private WebHookMockParams webHookMockParams;

    @Mock
    private okhttp3.OkHttpClient httpClient;

    private PagamentoGateway pagamentoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(bancoDeDadosContexto.getRedisCommands()).thenReturn(redisCommands);
        webHookMockParams = new WebHookMockParams("http://example.com/", httpClient);
        pagamentoGateway = new PagamentoGateway(bancoDeDadosContexto, webHookMockParams);
    }

    @Test
    void testCadastrar() {
        // Arrange
        Pagamento pagamento = new Pagamento(1, StatusPagamento.APROVADO);

        // Act
        pagamentoGateway.cadastrar(pagamento);

        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> fieldCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        verify(redisCommands).hset(keyCaptor.capture(), fieldCaptor.capture(), valueCaptor.capture());

        // Assert
        assertEquals("pagamento:1", keyCaptor.getValue());
        assertEquals("status", fieldCaptor.getValue());
        assertEquals("APROVADO", valueCaptor.getValue());
    }

    @Test
    void testEfetuarPagamento_Successful() throws Exception {
        // Arrange
        Integer idPedido = 1;

        OkHttpClient mockHttpClient = mock(OkHttpClient.class);
        Call mockCall = mock(Call.class);
        Response mockResponse = mock(Response.class);

        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);

        when(mockCall.execute()).thenReturn(mockResponse);

        when(mockResponse.isSuccessful()).thenReturn(true);

        WebHookMockParams mockWebHookMockParams = new WebHookMockParams("http://example.com/", mockHttpClient);
        PagamentoGateway pagamentoGateway = new PagamentoGateway(mock(BancoDeDadosContextoInterface.class), mockWebHookMockParams);

        // Act
        Boolean result = pagamentoGateway.efetuarPagamento(idPedido);

        // Assert
        assertTrue(result);
        verify(mockHttpClient).newCall(any(Request.class));
        try {
            verify(mockCall).execute();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testEfetuarPagamento_Failure() {
        // Arrange
        Integer idPedido = 1;

        Call mockCall = mock(Call.class);

        when(httpClient.newCall(any(Request.class))).thenReturn(mockCall);

        try {
            when(mockCall.execute()).thenThrow(new IOException("Request failed"));
        } catch (IOException e) {
            fail("IOException should be mocked, no need to throw here");
        }

        // Act
        Boolean result = pagamentoGateway.efetuarPagamento(idPedido);

        // Assert
        assertFalse(result);
        verify(httpClient).newCall(any(Request.class));
        try {
            verify(mockCall).execute();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testConsultaStatus_ExistingPayment() {
        // Arrange
        Integer idPedido = 1;
        when(redisCommands.hget("pagamento:" + idPedido, "status")).thenReturn("APROVADO");

        // Act
        StatusPagamento status = pagamentoGateway.consultaStatus(idPedido);

        // Assert
        assertEquals(StatusPagamento.APROVADO, status);
    }

    @Test
    void testConsultaStatus_NonExistingPayment() {
        // Arrange
        Integer idPedido = 1;
        when(redisCommands.hget("pagamento:" + idPedido, "status")).thenReturn(null);

        // Act
        StatusPagamento status = pagamentoGateway.consultaStatus(idPedido);

        // Assert
        assertNull(status);
    }

    @Test
    void testVerificaPagamentoExiste_ExistingPayment() {
        // Arrange
        Integer idPedido = 1;
        when(redisCommands.exists("pagamento:" + idPedido)).thenReturn(1L);

        // Act
        boolean exists = pagamentoGateway.verificaPagamentoExiste(idPedido);

        // Assert
        assertTrue(exists);
    }

    @Test
    void testVerificaPagamentoExiste_NonExistingPayment() {
        // Arrange
        Integer idPedido = 1;
        when(redisCommands.exists("pagamento:" + idPedido)).thenReturn(0L);

        // Act
        boolean exists = pagamentoGateway.verificaPagamentoExiste(idPedido);

        // Assert
        assertFalse(exists);
    }
}
