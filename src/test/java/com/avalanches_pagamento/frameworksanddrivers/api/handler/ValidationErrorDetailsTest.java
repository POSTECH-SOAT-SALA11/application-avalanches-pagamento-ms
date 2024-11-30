package com.avalanches_pagamento.frameworksanddrivers.api.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationErrorDetailsTest {

    private ValidationErrorDetails validationErrorDetails;

    @BeforeEach
    void setUp() {
        // Set up the object to be tested
        validationErrorDetails = new ValidationErrorDetails("fieldName", "error message");
    }

    @Test
    void testConstructor() {
        // Test that the constructor initializes the object properly
        assertEquals("fieldName", validationErrorDetails.getField());
        assertEquals("error message", validationErrorDetails.getMessage());
    }

    @Test
    void testGetField() {
        // Test that the getter for field works
        assertEquals("fieldName", validationErrorDetails.getField());
    }

    @Test
    void testGetMessage() {
        // Test that the getter for message works
        assertEquals("error message", validationErrorDetails.getMessage());
    }

    @Test
    void testSetField() {
        // Test that the setter for field works
        validationErrorDetails.setField("newFieldName");
        assertEquals("newFieldName", validationErrorDetails.getField());
    }

    @Test
    void testSetMessage() {
        // Test that the setter for message works
        validationErrorDetails.setMessage("new error message");
        assertEquals("new error message", validationErrorDetails.getMessage());
    }
}
