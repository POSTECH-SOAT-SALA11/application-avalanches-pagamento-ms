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

        // Capture the Redis key and value passed to hset
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> fieldCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        verify(redisCommands).hset(keyCaptor.capture(), fieldCaptor.capture(), valueCaptor.capture());

        // Assert: Verify that the key and value are correct
        assertEquals("pagamento:1", keyCaptor.getValue());
        assertEquals("status", fieldCaptor.getValue());
        assertEquals("APROVADO", valueCaptor.getValue());
    }

    @Test
    void testEfetuarPagamento_Successful() throws Exception {
        // Arrange
        Integer idPedido = 1;

        // Mock the HttpClient, Call, and Response
        OkHttpClient mockHttpClient = mock(OkHttpClient.class);
        Call mockCall = mock(Call.class);
        Response mockResponse = mock(Response.class);

        // Mock newCall() to return the mock Call
        when(mockHttpClient.newCall(any(Request.class))).thenReturn(mockCall);

        // Mock execute() to return the mock Response
        when(mockCall.execute()).thenReturn(mockResponse);

        // Mock isSuccessful() to return true for a successful request
        when(mockResponse.isSuccessful()).thenReturn(true);

        // Pass the mockHttpClient to the PagamentoGateway constructor
        WebHookMockParams mockWebHookMockParams = new WebHookMockParams("http://example.com/", mockHttpClient);
        PagamentoGateway pagamentoGateway = new PagamentoGateway(mock(BancoDeDadosContextoInterface.class), mockWebHookMockParams);

        // Act
        Boolean result = pagamentoGateway.efetuarPagamento(idPedido);

        // Assert
        assertTrue(result);  // Since we mock isSuccessful() to return true
        verify(mockHttpClient).newCall(any(Request.class));  // Verify that newCall() was called
        try {
            verify(mockCall).execute();  // Verify that execute() was called on the mock Call object
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }


    @Test
    void testEfetuarPagamento_Failure() {
        // Arrange
        Integer idPedido = 1;

        // Mock the Call object
        Call mockCall = mock(Call.class);

        // Mock the HTTP client to return the mock Call
        when(httpClient.newCall(any(Request.class))).thenReturn(mockCall);

        // Mock the execute method to throw an exception
        try {
            when(mockCall.execute()).thenThrow(new IOException("Request failed"));
        } catch (IOException e) {
            fail("IOException should be mocked, no need to throw here");
        }

        // Act
        Boolean result = pagamentoGateway.efetuarPagamento(idPedido);

        // Assert
        assertFalse(result);  // We expect false because the request failed
        verify(httpClient).newCall(any(Request.class));  // Verify that newCall() was called
        try {
            verify(mockCall).execute();  // Verify that execute() was called on the mock Call object
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
