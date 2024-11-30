package com.avalanches_pagamento.frameworksanddrivers.api.dto;

import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WebhookParamsTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        // Create a ValidatorFactory and Validator instance for validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidWebhookParams() {
        // Arrange
        StatusPagamento statusPagamento = StatusPagamento.APROVADO;
        WebhookParams webhookParams = new WebhookParams(123, statusPagamento);

        // Act
        Set<ConstraintViolation<WebhookParams>> violations = validator.validate(webhookParams);

        // Assert
        assertTrue(violations.isEmpty(), "There should be no validation violations for valid input");
    }

    @Test
    void testIdPedidoIsNull() {
        // Arrange
        StatusPagamento statusPagamento = StatusPagamento.APROVADO;
        WebhookParams webhookParams = new WebhookParams(null, statusPagamento);

        // Act
        Set<ConstraintViolation<WebhookParams>> violations = validator.validate(webhookParams);

        // Assert
        assertFalse(violations.isEmpty(), "There should be a validation violation when idPedido is null");
        assertEquals("id do pedido não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void testStatusIsNull() {
        // Arrange
        WebhookParams webhookParams = new WebhookParams(123, null);

        // Act
        Set<ConstraintViolation<WebhookParams>> violations = validator.validate(webhookParams);

        // Assert
        assertFalse(violations.isEmpty(), "There should be a validation violation when status is null");
        assertEquals("status não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void testBothIdPedidoAndStatusAreNull() {
        // Arrange
        WebhookParams webhookParams = new WebhookParams(null, null);

        // Act
        Set<ConstraintViolation<WebhookParams>> violations = validator.validate(webhookParams);

        // Assert
        assertEquals(2, violations.size(), "There should be two validation violations when both idPedido and status are null");
        assertTrue(violations.stream().anyMatch(v -> "id do pedido não pode ser nulo".equals(v.getMessage())));
        assertTrue(violations.stream().anyMatch(v -> "status não pode ser nulo".equals(v.getMessage())));
    }
}
