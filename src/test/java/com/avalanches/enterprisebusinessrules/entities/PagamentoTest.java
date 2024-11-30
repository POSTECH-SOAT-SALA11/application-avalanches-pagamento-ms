package com.avalanches.enterprisebusinessrules.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Integer expectedIdPedido = 101;
        StatusPagamento expectedStatusPagamento = StatusPagamento.APROVADO;

        // Act
        Pagamento pagamento = new Pagamento(expectedIdPedido, expectedStatusPagamento);

        // Assert
        assertEquals(expectedIdPedido, pagamento.getIdPedido(), "The ID of the pedido should match the expected value");
        assertEquals(expectedStatusPagamento, pagamento.getStatusPagamento(), "The statusPagamento should match the expected value");
    }
}
