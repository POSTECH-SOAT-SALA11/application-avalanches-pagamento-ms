package com.avalanches_pagamento.enterprisebusinessrules.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusPagamentoTest {

    @Test
    void testEnumValues() {
        assertEquals("Aprovado", StatusPagamento.APROVADO.getValue(), "APROVADO should return 'Aprovado'");
        assertEquals("Reprovado", StatusPagamento.REPROVADO.getValue(), "REPROVADO should return 'Reprovado'");
    }

    @Test
    void testEnumIntegrity() {
        StatusPagamento[] values = StatusPagamento.values();
        assertEquals(2, values.length, "StatusPagamento enum should have exactly 2 constants");
        assertArrayEquals(new StatusPagamento[]{StatusPagamento.APROVADO, StatusPagamento.REPROVADO}, values, "Enum constants do not match expected values");
    }
}
