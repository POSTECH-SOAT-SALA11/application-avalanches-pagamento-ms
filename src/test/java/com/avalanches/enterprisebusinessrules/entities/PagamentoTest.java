package com.avalanches.enterprisebusinessrules.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Integer expectedId = 1;
        Integer expectedIdPedido = 101;
        StatusPagamento expectedStatusPagamento = StatusPagamento.APROVADO;

        // Act
        Pagamento pagamento = new Pagamento(expectedId, expectedIdPedido, expectedStatusPagamento);

        // Assert
        assertEquals(expectedId, pagamento.getId(), "The ID should match the expected value");
        assertEquals(expectedIdPedido, pagamento.getIdPedido(), "The ID of the pedido should match the expected value");
        assertEquals(expectedStatusPagamento, pagamento.getStatusPagamento(), "The statusPagamento should match the expected value");
    }
}
