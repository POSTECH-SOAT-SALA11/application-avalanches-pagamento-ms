package com.avalanches.enterprisebusinessrules.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusPagamentoTest {

    @Test
    void testEnumValues() {
        // Assert that the enum constants are mapped correctly
        assertEquals("Aprovado", StatusPagamento.APROVADO.getValue(), "APROVADO should return 'Aprovado'");
        assertEquals("Reprovado", StatusPagamento.REPROVADO.getValue(), "REPROVADO should return 'Reprovado'");
    }

    @Test
    void testEnumIntegrity() {
        // Assert that the enum contains only the expected values
        StatusPagamento[] values = StatusPagamento.values();
        assertEquals(2, values.length, "StatusPagamento enum should have exactly 2 constants");
        assertArrayEquals(new StatusPagamento[]{StatusPagamento.APROVADO, StatusPagamento.REPROVADO}, values, "Enum constants do not match expected values");
    }
}
