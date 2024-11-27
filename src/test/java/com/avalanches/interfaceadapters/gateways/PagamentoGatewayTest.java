package com.avalanches.interfaceadapters.gateways;

import org.springframework.jdbc.core.JdbcTemplate;
import com.avalanches.enterprisebusinessrules.entities.Pagamento;
import com.avalanches.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches.frameworksanddrivers.api.dto.WebHookMockParams;
import com.avalanches.frameworksanddrivers.databases.interfaces.BancoDeDadosContextoInterface;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagamentoGatewayTest {

    @Mock
    private BancoDeDadosContextoInterface bancoDeDadosContexto;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private WebHookMockParams webHookMockParams;

    @Mock
    private OkHttpClient httpClient;

    private PagamentoGateway pagamentoGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webHookMockParams = mock(WebHookMockParams.class);
        when(webHookMockParams.getHttpClient()).thenReturn(httpClient);
        when(webHookMockParams.getBaseUrl()).thenReturn("http://mock.url/");
        when(bancoDeDadosContexto.getJdbcTemplate()).thenReturn(jdbcTemplate);
        pagamentoGateway = new PagamentoGateway(bancoDeDadosContexto, webHookMockParams);
    }

    @Test
    void testCadastrar() {
        Pagamento pagamento = new Pagamento(0, 1, StatusPagamento.APROVADO);

        pagamentoGateway.cadastrar(pagamento);

        ArgumentCaptor<Object[]> captor = ArgumentCaptor.forClass(Object[].class);
        verify(jdbcTemplate).update(eq("INSERT INTO pagamento (id_pedido, status) VALUES (?,?)"),
                captor.capture());
        Object[] capturedArgs = captor.getValue();
        assertEquals(1, capturedArgs[0]);
        assertEquals("APROVADO", capturedArgs[1]);
    }

    @Test
    void testConsultaStatus() {
        int idPedido = 1;
        when(jdbcTemplate.queryForObject(
                eq("SELECT status FROM pagamento WHERE id_pedido = ?"),
                any(Object[].class),
                eq(String.class)
        )).thenReturn("APROVADO");

        StatusPagamento status = pagamentoGateway.consultaStatus(idPedido);

        assertEquals(StatusPagamento.APROVADO, status);
        verify(jdbcTemplate).queryForObject(
                eq("SELECT status FROM pagamento WHERE id_pedido = ?"),
                any(Object[].class),
                eq(String.class)
        );
    }

    @Test
    void testConsultaStatus_InvalidStatus() {
        int idPedido = 1;
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(String.class)))
                .thenReturn("INVALID");

        assertThrows(IllegalArgumentException.class, () -> pagamentoGateway.consultaStatus(idPedido));
    }

    @Test
    void testVerificaPagamentoExiste_True() {
        int idPedido = 1;
        when(jdbcTemplate.queryForObject(
                eq("SELECT COUNT(*) FROM pagamento WHERE id_pedido = ?"),
                any(Object[].class),
                eq(Integer.class)
        )).thenReturn(1);

        boolean exists = pagamentoGateway.verificaPagamentoExiste(idPedido);

        assertTrue(exists);
    }

    @Test
    void testVerificaPagamentoExiste_False() {
        int idPedido = 1;
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(Integer.class)))
                .thenReturn(0);

        boolean exists = pagamentoGateway.verificaPagamentoExiste(idPedido);

        assertFalse(exists);
    }

    @Test
    void testVerificaPagamentoExiste_Exception() {
        int idPedido = 1;

        // Mocking JdbcTemplate to throw an exception
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(Integer.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Verifying that the method throws the expected exception
        assertThrows(EmptyResultDataAccessException.class, () -> pagamentoGateway.verificaPagamentoExiste(idPedido));
    }

}
