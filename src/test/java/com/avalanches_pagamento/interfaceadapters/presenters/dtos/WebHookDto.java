package com.avalanches_pagamento.interfaceadapters.presenters.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebHookDtoTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange: Create an instance of WebHookDto
        boolean sucesso = true;
        String mensagem = "Success";

        WebHookDto dto = new WebHookDto(sucesso, mensagem);

        // Act & Assert: Verify that the fields are correctly set
        assertEquals(sucesso, dto.sucesso());
        assertEquals(mensagem, dto.mensagem());
    }

    @Test
    void testEquality() {
        // Arrange: Create two instances of WebHookDto with the same data
        WebHookDto dto1 = new WebHookDto(true, "Success");
        WebHookDto dto2 = new WebHookDto(true, "Success");

        // Act & Assert: Verify that the two instances are considered equal
        assertEquals(dto1, dto2);
    }

    @Test
    void testEqualityWithDifferentData() {
        // Arrange: Create two instances of WebHookDto with different data
        WebHookDto dto1 = new WebHookDto(true, "Success");
        WebHookDto dto2 = new WebHookDto(false, "Failure");

        // Act & Assert: Verify that the two instances are not considered equal
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode() {
        // Arrange: Create two instances of WebHookDto with the same data
        WebHookDto dto1 = new WebHookDto(true, "Success");
        WebHookDto dto2 = new WebHookDto(true, "Success");

        // Act & Assert: Verify that instances with the same data have the same hashCode
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testHashCodeWithDifferentData() {
        // Arrange: Create two instances of WebHookDto with different data
        WebHookDto dto1 = new WebHookDto(true, "Success");
        WebHookDto dto2 = new WebHookDto(false, "Failure");

        // Act & Assert: Verify that instances with different data have different hashCodes
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        // Arrange: Create an instance of WebHookDto
        WebHookDto dto = new WebHookDto(true, "Success");

        // Act: Get the string representation of the instance
        String expectedToString = "WebHookDto[sucesso=true, mensagem=Success]";

        // Assert: Verify that the toString method returns the expected string
        assertEquals(expectedToString, dto.toString());
    }
}
