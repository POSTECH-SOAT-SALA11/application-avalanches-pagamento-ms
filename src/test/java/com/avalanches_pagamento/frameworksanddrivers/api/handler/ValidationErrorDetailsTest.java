package com.avalanches_pagamento.frameworksanddrivers.api.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationErrorDetailsTest {

    private ValidationErrorDetails validationErrorDetails;

    @BeforeEach
    void setUp() {
        validationErrorDetails = new ValidationErrorDetails("fieldName", "error message");
    }

    @Test
    void testConstructor() {
        assertEquals("fieldName", validationErrorDetails.getField());
        assertEquals("error message", validationErrorDetails.getMessage());
    }

    @Test
    void testGetField() {
        assertEquals("fieldName", validationErrorDetails.getField());
    }

    @Test
    void testGetMessage() {
        assertEquals("error message", validationErrorDetails.getMessage());
    }

    @Test
    void testSetField() {
        validationErrorDetails.setField("newFieldName");
        assertEquals("newFieldName", validationErrorDetails.getField());
    }

    @Test
    void testSetMessage() {
        validationErrorDetails.setMessage("new error message");
        assertEquals("new error message", validationErrorDetails.getMessage());
    }
}
